import numpy as np
import pickle

class Tensor:
    """参数类，封装数据和梯度"""
    def __init__(self, data):
        self.data = data    # 参数值（numpy 数组），真正需要保存的数据
        self.grad = None    # 梯度值（numpy 数组），可以理解为一个中间变量

class BaseModel:

    def __init__(self, input_dim):
        """
        Args:
            input_dim: int, 输入特征维度
        """
        # 初始化权重和偏置参数
        self.w = Tensor(np.random.randn(input_dim))  # 权重参数 (input_dim,)
        self.b = Tensor(np.zeros(1))                 # 偏置参数 (1,)
        self.input = None  # 保存输入数据，用于反向传播

    def parameters(self):
        """返回模型参数列表"""
        return [self.w, self.b]

    """模型基类"""
    def __call__(self, input):
        """前向传播（由子类实现）"""
        result = self.forward(input)
        return result

    def forward(self, input):
        raise NotImplementedError("Subclasses must implement __call__()")

    def save(self, weight_path):
        weights_dict = {
            "w": self.w.data,
            "b": self.b.data
        }
        try:
            # 保存权重
            with open(weight_path, 'wb') as f:
                pickle.dump(weights_dict, f)
            print(f"权重已保存到 {weight_path}")
        except Exception as e:
            print(f"保存权重时出错: {str(e)}")

    def load(self, weight_path):
        """从文件加载权重字典"""
        try:
            # 加载权重
            with open(weight_path, 'rb') as f:
                weights_dict = pickle.load(f)
            print(f"权重已从 {weight_path} 加载")

            # 验证加载的权重
            if "w" not in weights_dict or "b" not in weights_dict:
                raise KeyError("权重字典缺少必要的键")

        except FileNotFoundError:
            print(f"错误: 文件 {weight_path} 未找到")
            return None
        except Exception as e:
            print(f"加载权重时出错: {str(e)}")
            return None
        self.w.data = weights_dict["w"]
        self.b.data = weights_dict["b"]


class LinearRegression(BaseModel):
    """线性回归模型"""
    def __init__(self, input_dim):
        """
        Args:
            input_dim: int, 输入特征维度
        """
        super().__init__(input_dim)

    def forward(self, input):
        """前向传播: output = input @ w + b"""
        # 确保输入始终为二维数组（兼容单样本和批量）
        if input.ndim == 1:
            self.input = input.reshape(1, -1)
        else:
            self.input = input
        return np.dot(self.input, self.w.data) + self.b.data



class LogicRegression(BaseModel):
    """逻辑回归模型（输出为 logits）"""
    def __init__(self, input_dim):
        """
        Args:
            input_dim: int, 输入特征维度
        """
        super().__init__(input_dim)

    def forward(self, input):
        """前向传播: logits = input @ w + b"""
        # 确保输入始终为二维数组（兼容单样本和批量）
        if input.ndim == 1:
            self.input = input.reshape(1, -1)
        else:
            self.input = input
        z = np.dot(self.input, self.w.data) + self.b.data
        # 计算 Sigmoid 概率（数值稳定处理）
        output = 1 / (1 + np.exp(-z))

        output = output.reshape(-1, 1)  # 形状 (n_samples, 1)

        return output

if __name__ == '__main__':
    # np.random.seed(0)
    x = np.random.randn(2, 3)
    y = (np.random.rand(2) > 0.5).astype(int).reshape(-1, 1)
    model = LogicRegression(3)
    # output = model(x)
    # model.save('model.pkl')
    # print(output)
    model.load('model.pkl')
    out = model.forward(x)