import json
import numpy as np
import cv2
import os
from typing import List, Tuple, Dict
from pathlib import Path


def points_to_mask(points: List[List[float]],
                   image_size: Tuple[int, int] = (640, 480),
                   class_id: int = 1,
                   line_thickness: int = 2) -> np.ndarray:
    """
    将点坐标列表转换为Mask图像，使用类别编号填充

    Args:
        points: 点坐标列表 [[x1, y1], [x2, y2], ...]
        image_size: 图像尺寸 (宽度, 高度)
        class_id: 类别编号
        line_thickness: 多边形线条粗细

    Returns:
        mask: Mask图像 (高度, 宽度)，像素值为类别编号
    """
    # 创建空白图像
    mask = np.zeros((image_size[1], image_size[0]), dtype=np.uint8)

    # 将点坐标转换为整数并调整为NumPy数组格式
    pts = np.array(points, dtype=np.int32)

    # 确保点坐标在图像范围内
    pts[:, 0] = np.clip(pts[:, 0], 0, image_size[0] - 1)
    pts[:, 1] = np.clip(pts[:, 1], 0, image_size[1] - 1)

    # 使用类别编号填充多边形
    cv2.fillPoly(mask, [pts], color=class_id)

    # 绘制多边形轮廓（使用类别编号）
    cv2.polylines(mask, [pts], isClosed=True, color=class_id, thickness=line_thickness)

    return mask


def json_to_mask(json_data: dict,
                 image_size: Tuple[int, int] = (640, 480),
                 class_mapping: Dict[str, int] = None) -> np.ndarray:
    """
    从JSON数据中提取点坐标并转换为Mask图像，支持多类别

    Args:
        json_data: 包含点坐标的JSON数据
        image_size: 图像尺寸 (宽度, 高度)
        class_mapping: 类别名称到编号的映射字典

    Returns:
        mask: Mask图像 (高度, 宽度)，像素值为类别编号
    """
    # 如果没有提供类别映射，使用默认映射
    if class_mapping is None:
        class_mapping = {"object": 1}  # 默认映射

    # 创建空白Mask
    mask = np.zeros((image_size[1], image_size[0]), dtype=np.uint8)

    # 处理每个形状
    for shape in json_data.get('shapes', []):
        if shape.get('shape_type') == 'polygon':
            points = shape.get('points', [])
            label = shape.get('label', 'object')

            # 获取类别编号
            class_id = class_mapping.get(label, 0)  # 0表示背景

            if class_id > 0 and points:  # 只处理有效类别和非空点集
                # 创建当前类别的Mask
                class_mask = points_to_mask(points, image_size, class_id)

                # 合并到总Mask中
                mask = np.where(class_mask > 0, class_mask, mask)

    return mask


def process_json_directory(json_dir: str,
                           output_dir: str,
                           image_size: Tuple[int, int] = (640, 480),
                           class_mapping: Dict[str, int] = None):
    """
    处理目录中的所有JSON文件并生成对应的Mask图像

    Args:
        json_dir: JSON文件目录路径
        output_dir: 输出Mask图像目录路径
        image_size: 图像尺寸 (宽度, 高度)
        class_mapping: 类别名称到编号的映射字典
    """
    # 确保输出目录存在
    os.makedirs(output_dir, exist_ok=True)

    # 获取所有JSON文件
    json_files = [f for f in os.listdir(json_dir) if f.endswith('.json')]

    print(f"找到 {len(json_files)} 个JSON文件")

    # 处理每个JSON文件
    for json_file in json_files:
        json_path = os.path.join(json_dir, json_file)

        # 读取JSON文件
        with open(json_path, 'r', encoding='utf-8') as f:
            json_data = json.load(f)

        # 生成Mask图像
        mask = json_to_mask(json_data, image_size, class_mapping)

        # 生成输出文件名
        base_name = os.path.splitext(json_file)[0]
        output_path = os.path.join(output_dir, f"{base_name}_mask.png")

        # 保存Mask图像
        cv2.imwrite(output_path, mask)
        print(f"已处理: {json_file} -> {os.path.basename(output_path)}")


# 使用示例
if __name__ == "__main__":
    # 定义类别映射（根据您的实际类别名称修改）
    class_mapping = {
        "person": 1,  # 棋盘格类别编号为1
        "background": 0,  # 背景类别编号为0
        # 可以添加更多类别...
    }

    # 定义图像尺寸（根据您的实际图像尺寸修改）
    image_size = (640, 480)  # (宽度, 高度)

    # 定义输入和输出目录
    json_dir = r"D:\MyData\pub-project\small-frame-demo\1-py-test\ai_study\torch\Unet\imgs\labelme_json"  # JSON文件目录
    output_dir = r"D:\MyData\pub-project\small-frame-demo\1-py-test\ai_study\torch\Unet\imgs\train_mask"  # 输出Mask目录

    # 处理目录中的所有JSON文件
    process_json_directory(json_dir, output_dir, image_size, class_mapping)

    print("处理完成!")