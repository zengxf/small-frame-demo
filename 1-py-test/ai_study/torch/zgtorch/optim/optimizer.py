import numpy as np


class BaseOptimizer:
    """优化器基类"""
    def zero_grad(self):
        """清空所有参数的梯度"""
        for param in self.params:
            if param.grad is not None:
                param.grad.fill(0)  # 将梯度置零而非设为 None，避免内存分配开销

    def step(self):
        """更新参数（由子类实现）"""
        raise NotImplementedError("Subclasses must implement step()")


class SGD(BaseOptimizer):
    """随机梯度下降优化器"""
    def __init__(self, params, lr=0.01):
        """
        Args:
            params: List[Parameter], 待优化的参数列表
            lr: float, 学习率
        """
        self.params = params
        self.lr = lr

    def step(self):
        """根据梯度更新参数"""
        for param in self.params:
            param.data -= self.lr * param.grad  # 参数更新公式: w = w - lr * grad



class Adam(BaseOptimizer):
    """Adam 优化器"""
    def __init__(self, params, lr=0.001, beta1=0.9, beta2=0.999, epsilon=1e-8):
        """
        Args:
            params: List[Parameter], 待优化的参数列表
            lr: float, 学习率（默认 0.001）
            beta1: float, 一阶矩衰减率（默认 0.9）
            beta2: float, 二阶矩衰减率（默认 0.999）
            epsilon: float, 数值稳定性常数（默认 1e-8）
        """
        super().__init__()
        self.params = params
        self.lr = lr
        self.beta1 = beta1
        self.beta2 = beta2
        self.epsilon = epsilon
        self.t = 0  # 时间步计数器

        # 初始化一阶矩（m）和二阶矩（v）为全零
        self.m = {param: np.zeros_like(param.data) for param in self.params}
        self.v = {param: np.zeros_like(param.data) for param in self.params}

    def step(self):
        """执行 Adam 参数更新"""
        self.t += 1  # 更新时间步
        for param in self.params:
            if param.grad is None:
                continue

            grad = param.grad  # 当前梯度

            # 更新一阶矩和二阶矩
            self.m[param] = self.beta1 * self.m[param] + (1 - self.beta1) * grad
            self.v[param] = self.beta2 * self.v[param] + (1 - self.beta2) * (grad ** 2)

            # 偏差校正
            m_hat = self.m[param] / (1 - self.beta1 ** self.t)
            v_hat = self.v[param] / (1 - self.beta2 ** self.t)

            # 更新参数
            param.data -= self.lr * m_hat / (np.sqrt(v_hat) + self.epsilon)