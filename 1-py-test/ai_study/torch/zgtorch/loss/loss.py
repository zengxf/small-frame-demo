import numpy as np


class BaseLoss:
    """损失函数基类"""
    def __call__(self, output, target):
        """计算损失（由子类实现）"""
        raise NotImplementedError("Subclasses must implement __call__()")

class MSELoss(BaseLoss):
    """均方误差损失函数"""
    def __init__(self, model):
        """
        Args:
            model: BaseModel, 模型实例，用于反向传播时访问输入数据
        """
        self.model = model  # 绑定模型，用于获取前向传播的输入数据
        self.output = None
        self.target = None

    def __call__(self, output, target):
        """计算损失: MSE = 1/n * Σ(output - target)^2"""
        self.output = output
        self.target = target
        self.n_samples = output.shape[0] if output.ndim > 0 else 1  # 动态获取样本数
        return np.mean((output - target) ** 2), self

    def backward(self):
        """反向传播计算梯度"""
        error = self.output - self.target  # 误差项，形状自动广播

        # 计算梯度（兼容单样本和批量）
        if self.model.input.ndim == 1:
            # 单样本输入 (input_dim,) -> 转为 (1, input_dim) 处理
            input_matrix = self.model.input.reshape(1, -1)
        else:
            input_matrix = self.model.input

        grad_w = (2.0 / self.n_samples) * np.dot(input_matrix.T, error)
        grad_b = (2.0 / self.n_samples) * np.sum(error)  # b 相当于常量为 1 的数组

        # 更新梯度
        self.model.w.grad = grad_w.reshape(self.model.w.data.shape)
        self.model.b.grad = grad_b


# ------------------------------ 交叉熵损失函数 ------------------------------
class CrossEntropyLoss(BaseLoss):
    """交叉熵损失函数（支持二分类）"""
    def __init__(self, model):
        """
        Args:
            model: BaseModel, 模型实例，用于反向传播时访问输入数据
        """
        self.model = model  # 绑定模型
        self.output = None  # 保存 logits
        self.target = None  # 保存真实标签
        self.n_samples = 0  # 样本数量

    def __call__(self, output, target):
        """计算损失: CrossEntropy = -1/n * Σ[y*log(p) + (1-y)*log(1-p)]"""
        self.output = output  # logits（模型直接输出的值）
        self.target = target.reshape(-1, 1)  # 转为列向量 (n_samples, 1)
        self.n_samples = output.shape[0]

        # 计算 Sigmoid 概率（数值稳定处理）
        epsilon = 1e-8  # 防止 log(0)
        p = np.clip(self.output, epsilon, 1 - epsilon)

        # 计算交叉熵损失
        loss = -np.mean(self.target * np.log(p) + (1 - self.target) * np.log(1 - p))
        return loss, self

    def backward(self):
        """反向传播计算梯度"""
        # 计算 Sigmoid 概率
        p = 1 / (1 + np.exp(-self.output))  # 形状 (n_samples, 1)

        # 计算梯度 (p - y) / n_samples
        error = (p - self.target) / self.n_samples  # 形状 (n_samples, 1)

        # 获取输入数据（已由模型保存为二维数组）
        input_matrix = self.model.input  # 形状 (n_samples, input_dim)

        # 计算参数梯度
        grad_w = np.dot(input_matrix.T, error)  # (input_dim, 1)
        grad_b = np.sum(error)                  # 标量

        # 更新模型参数的梯度（确保形状匹配）
        self.model.w.grad = grad_w.reshape(self.model.w.data.shape)
        self.model.b.grad = grad_b