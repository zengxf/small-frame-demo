import cv2
import numpy as np
import onnxruntime as ort
from typing import Optional, Union, Tuple, List
import time
import sys
import os

sys.path.append(os.path.dirname(__file__))


class ONNXSegmentationPredictor:
    def __init__(self, model_path: str, device: str = 'cpu', num_classes: int = 2):
        """
        初始化 ONNX 语义分割模型预测器

        Args:
            model_path: ONNX 模型文件路径
            device: 推理设备 ('cpu' 或 'cuda')
            num_classes: 类别数量
        """
        self.model_path = model_path
        self.device = device
        self.num_classes = num_classes

        # 初始化 ONNX Runtime session
        providers = ['CPUExecutionProvider']
        if device.lower() == 'cuda':
            providers = ['CUDAExecutionProvider', 'CPUExecutionProvider']

        self.session = ort.InferenceSession(model_path, providers=providers)

        # 获取输入输出信息
        self.input_name = self.session.get_inputs()[0].name
        self.output_name = self.session.get_outputs()[0].name
        self.input_shape = self.session.get_inputs()[0].shape
        self.output_shape = self.session.get_outputs()[0].shape

        print(f"✓ 模型加载成功: {model_path}")
        print(f"  输入名称: {self.input_name}, 形状: {self.input_shape}")
        print(f"  输出名称: {self.output_name}, 形状: {self.output_shape}")
        print(f"  推理设备: {device}")
        print(f"  类别数量: {num_classes}")

    def preprocess(self, image: np.ndarray) -> np.ndarray:
        """
        预处理输入图像

        Args:
            image: 输入图像 (H, W, C) 或 (H, W)

        Returns:
            预处理后的张量 (1, C, H, W)
        """
        # 确保图像为正确的通道数
        if len(image.shape) == 2:  # 灰度图
            image = np.expand_dims(image, axis=2)  # 添加通道维度

        # 调整尺寸到模型期望的尺寸（如果需要）
        target_height, target_width = self.input_shape[2], self.input_shape[3]
        if image.shape[:2] != (target_height, target_width):
            image = cv2.resize(image, (target_width, target_height), interpolation=cv2.INTER_LINEAR)

        # 转换通道顺序 HWC -> CHW
        if len(image.shape) == 3:
            image = np.transpose(image, (2, 0, 1))

        # 归一化到 [0, 1]
        if image.dtype == np.uint8:
            image = image.astype(np.float32) / 255.0
        elif image.max() > 1.0:
            image = image.astype(np.float32) / 255.0

        # 添加批次维度 (C, H, W) -> (1, C, H, W)
        image = np.expand_dims(image, 0)

        return image.astype(np.float32)

    def postprocess(self, output: np.ndarray) -> Tuple[np.ndarray, np.ndarray]:
        """
        后处理模型输出

        Args:
            output: 模型输出 (1, num_classes, H, W) 或 (1, 1, H, W)

        Returns:
            class_mask: 类别分割掩码 (H, W)
            confidence_map: 置信度图 (H, W)
        """
        # 移除批次维度
        output = output.squeeze(0)

        # 处理二分类和多分类情况
        if self.num_classes == 1 or self.num_classes == 2:
            # 二分类情况，使用sigmoid激活
            confidence_map = 1 / (1 + np.exp(-output.squeeze(0)))  # 应用sigmoid
            class_mask = (confidence_map > 0.5).astype(np.uint8)
        else:
            # 多分类情况，使用softmax
            output = np.exp(output - np.max(output, axis=0, keepdims=True))  # 数值稳定性
            softmax_output = output / np.sum(output, axis=0, keepdims=True)
            class_mask = np.argmax(softmax_output, axis=0).astype(np.uint8)
            confidence_map = np.max(softmax_output, axis=0)

        return class_mask, confidence_map

    def predict(self, image: np.ndarray) -> Tuple[np.ndarray, np.ndarray, float]:
        """
        执行语义分割推理

        Args:
            image: 输入图像

        Returns:
            class_mask: 类别分割掩码 (H, W)
            confidence_map: 置信度图 (H, W)
            inference_time: 推理时间(ms)
        """
        # 预处理
        input_tensor = self.preprocess(image)

        # 推理
        start_time = time.time()
        outputs = self.session.run(
            [self.output_name],
            {self.input_name: input_tensor}
        )
        inference_time = (time.time() - start_time) * 1000  # 转换为毫秒

        # 后处理
        class_mask, confidence_map = self.postprocess(outputs[0])

        return class_mask, confidence_map, inference_time

    def predict_batch(self, images: list) -> list:
        """
        批量预测

        Args:
            images: 图像列表

        Returns:
            结果列表，每个元素为 (class_mask, confidence_map, inference_time)
        """
        results = []
        for image in images:
            result = self.predict(image)
            results.append(result)

        return results

    def visualize_result(self, original_image: np.ndarray, class_mask: np.ndarray,
                         confidence_map: np.ndarray, save_path: Optional[str] = None) -> np.ndarray:
        """
        可视化语义分割结果

        Args:
            original_image: 原始图像
            class_mask: 类别分割掩码
            confidence_map: 置信度图
            save_path: 保存路径（可选）

        Returns:
            可视化结果图像
        """
        # 确保原始图像为3通道
        if len(original_image.shape) == 2:
            original_image = cv2.cvtColor(original_image, cv2.COLOR_GRAY2BGR)
        elif original_image.shape[2] == 1:
            original_image = cv2.cvtColor(original_image, cv2.COLOR_GRAY2BGR)

        # 调整大小以匹配原始图像
        if class_mask.shape != original_image.shape[:2]:
            class_mask = cv2.resize(class_mask, (original_image.shape[1], original_image.shape[0]),
                                    interpolation=cv2.INTER_NEAREST)
            confidence_map = cv2.resize(confidence_map, (original_image.shape[1], original_image.shape[0]),
                                        interpolation=cv2.INTER_LINEAR)

        # 创建彩色分割掩码
        if self.num_classes <= 2:
            # 二分类：使用绿色显示前景
            color_mask = np.zeros_like(original_image)
            color_mask[class_mask > 0] = [0, 255, 0]  # 绿色显示前景
        else:
            # 多分类：为每个类别分配不同颜色
            color_mask = np.zeros_like(original_image)
            colors = [
                [255, 0, 0],  # 红色 - 类别1
                [0, 255, 0],  # 绿色 - 类别2
                [0, 0, 255],  # 蓝色 - 类别3
                [255, 255, 0],  # 青色 - 类别4
                [255, 0, 255],  # 品红 - 类别5
                [0, 255, 255],  # 黄色 - 类别6
            ]
            for i in range(min(self.num_classes, len(colors))):
                color_mask[class_mask == i] = colors[i]

        # 叠加显示
        overlay = cv2.addWeighted(original_image, 0.7, color_mask, 0.3, 0)

        # 创建置信度热力图
        confidence_heatmap = cv2.applyColorMap((confidence_map * 255).astype(np.uint8), cv2.COLORMAP_JET)

        # 调整大小以匹配
        if original_image.shape[:2] != confidence_heatmap.shape[:2]:
            confidence_heatmap = cv2.resize(confidence_heatmap,
                                            (original_image.shape[1], original_image.shape[0]))

        # 拼接所有图像
        result_image = np.vstack([
            np.hstack([original_image, color_mask]),
            np.hstack([overlay, confidence_heatmap])
        ])

        if save_path:
            cv2.imwrite(save_path, result_image)

        return result_image

    def get_model_info(self) -> dict:
        """获取模型信息"""
        return {
            'input_name': self.input_name,
            'output_name': self.output_name,
            'input_shape': self.input_shape,
            'output_shape': self.output_shape,
            'device': self.device,
            'num_classes': self.num_classes
        }

    def calculate_iou(self, pred_mask: np.ndarray, gt_mask: np.ndarray) -> float:
        """
        计算IoU（Intersection over Union）

        Args:
            pred_mask: 预测掩码
            gt_mask: 真实掩码

        Returns:
            iou: IoU值
        """
        # 确保掩码形状一致
        if pred_mask.shape != gt_mask.shape:
            pred_mask = cv2.resize(pred_mask, (gt_mask.shape[1], gt_mask.shape[0]),
                                   interpolation=cv2.INTER_NEAREST)

        # 计算交集和并集
        intersection = np.logical_and(pred_mask, gt_mask).sum()
        union = np.logical_or(pred_mask, gt_mask).sum()

        # 避免除以零
        if union == 0:
            return 0.0

        return intersection / union

    def calculate_dice(self, pred_mask: np.ndarray, gt_mask: np.ndarray) -> float:
        """
        计算Dice系数

        Args:
            pred_mask: 预测掩码
            gt_mask: 真实掩码

        Returns:
            dice: Dice系数
        """
        # 确保掩码形状一致
        if pred_mask.shape != gt_mask.shape:
            pred_mask = cv2.resize(pred_mask, (gt_mask.shape[1], gt_mask.shape[0]),
                                   interpolation=cv2.INTER_NEAREST)

        # 计算交集和总和
        intersection = np.logical_and(pred_mask, gt_mask).sum()
        total = pred_mask.sum() + gt_mask.sum()

        # 避免除以零
        if total == 0:
            return 0.0

        return 2 * intersection / total


# 使用示例
if __name__ == "__main__":
    # 初始化预测器
    predictor = ONNXSegmentationPredictor(
        model_path="./checkpoints/model_1x480x640.onnx",
        device="cuda",  # 使用GPU加速
        num_classes=2  # 二分类问题
    )

    # 加载测试图像
    test_image = cv2.imread("./imgs/train_img/000021.jpg")

    # 执行预测
    class_mask, confidence_map, inference_time = predictor.predict(test_image)

    # 打印推理时间
    print(f"推理时间: {inference_time:.2f}ms")

    # 可视化结果
    result_image = predictor.visualize_result(test_image, class_mask, confidence_map)

    # 显示结果
    cv2.imshow("Segmentation Result", result_image)
    cv2.waitKey(0)
    cv2.destroyAllWindows()