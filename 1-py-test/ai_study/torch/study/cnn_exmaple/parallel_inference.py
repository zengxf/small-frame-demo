import torch
import threading
import time
from PIL import Image
from torchvision import transforms
import torchvision


def getMyModel(model_path):
    model = torchvision.models.efficientnet_b1(pretrained=False)
    model.classifier[1] = torch.nn.Linear(model.classifier[1].in_features, 2)
    model.load_state_dict(torch.load(model_path)['model_state_dict'])

    return model
class InferenceModel:
    def __init__(self, model_path, device):
        """
        初始化并加载单个模型
        :param model_path: 模型路径
        :param device: 计算设备（'cpu' 或 'cuda'）
        """
        # 加载模型
        #
        self.model = getMyModel(model_path)
        self.model = self.model.to(device).eval()  # 加载并切换到评估模式
        self.device = device

    def get_max_probability_class(self, input_data):
        """
        获取模型对输入数据的最大概率类别
        :param input_data: 输入数据
        :return: 类别索引和最大概率
        """
        with torch.no_grad():
            input_data = input_data.to(self.device)
            output = self.model(input_data)  # 执行推理
            # 获取每个样本的最大概率和对应的类
            max_probs, max_classes = torch.max(output, dim=1)
            return max_classes.item(), max_probs.item()  # 返回类别索引和概率


class ParallelInference:
    def __init__(self, model_paths, device):
        """
        初始化并加载两个模型
        :param model_paths: 模型路径列表，长度为 2
        :param device: 计算设备（例如 'cuda' 或 'cpu'）
        """
        if len(model_paths) != 2:
            raise ValueError("Only two model paths are supported.")

        # 加载两个模型并封装成 InferenceModel 类
        self.models = [InferenceModel(model_paths[0], device), InferenceModel(model_paths[1], device)]
        self.device = device

    def run_parallel_inference(self, input_data):
        """
        启动两个线程进行并行推理，分别处理同一输入数据
        :param input_data: 输入数据
        :return: 两个模型的推理结果字典
        """
        result_dict = {}

        # 创建线程
        threads = []
        thread1 = threading.Thread(target=self._get_max_probability, args=(0, input_data, result_dict))  # 对第一个模型进行推理
        thread2 = threading.Thread(target=self._get_max_probability, args=(1, input_data, result_dict))  # 对第二个模型进行推理
        threads.append(thread1)
        threads.append(thread2)

        # 启动线程
        thread1.start()
        thread2.start()

        # 等待所有线程完成
        for thread in threads:
            thread.join()

        return result_dict

    def _get_max_probability(self, model_idx, input_data, result_dict):
        """
        获取指定模型的最大概率类别
        :param model_idx: 模型的索引（0 或 1）
        :param input_data: 输入数据
        :param result_dict: 存储结果的字典
        """
        max_class, max_prob = self.models[model_idx].get_max_probability_class(input_data)
        result_dict[model_idx] = (max_class, max_prob)


def load_image(image_path, device, transform=None):
    """
    读取并预处理图片
    :param image_path: 图片路径
    :param device: 计算设备（'cpu' 或 'cuda'）
    :param transform: 预处理操作
    :return: 预处理后的张量
    """
    image = Image.open(image_path).convert('RGB')  # 打开图片并转换为 RGB 模式
    if transform:
        image = transform(image)  # 对图片进行预处理（如大小调整、归一化等）
    image = image.unsqueeze(0)  # 扩展维度，变成 [1, 3, H, W] 形状
    return image.to(device)


# 示例用法
if __name__ == "__main__":
    # 假设你有两个 .pt 模型文件路径
    model_paths = ['./best.pt', './best.pt']  # 替换为你自己的路径
    device = 'cuda' if torch.cuda.is_available() else 'cpu'

    # 预处理的图像变换操作
    transform = transforms.Compose([
        transforms.Resize((224, 224)),  # 调整为模型输入的大小
        transforms.ToTensor(),  # 转换为 Tensor
        transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])  # 标准化
    ])

    # 读取并预处理同一张图片
    image_path = "training_validation_curve_fake.png"  # 替换为实际的图片路径
    input_data = load_image(image_path, device, transform)

    # 初始化 ParallelInference 类
    parallel_inference = ParallelInference(model_paths, device)

    # 执行并行推理
    start_time = time.time()
    results = parallel_inference.run_parallel_inference(input_data)
    end_time = time.time()

    # 打印推理结果
    print(f"Inference results: {results}")
    print(f"Total time taken: {end_time - start_time:.4f} seconds")
