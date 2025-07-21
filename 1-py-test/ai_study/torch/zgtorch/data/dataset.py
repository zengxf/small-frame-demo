import numpy as np

class DataSet:
    """数据集类，封装特征和标签，支持迭代"""
    def __init__(self, file_path=None, batch_size=2):
        """
        Args:
            X: numpy array, 特征矩阵 (n_samples, n_features)
            y: numpy array, 标签向量 (n_samples, n_target)
        """
        self.file_path = file_path
        self.batch_size = batch_size
        self.feature, self.label = self.source()
        self.n_samples = self.feature.shape[0]

    def __iter__(self):
        """返回迭代器对象"""
        self.current = 0
        return self

    def __next__(self):
        """返回下一个样本 (input, target)，保持输入为二维数组"""
        if (self.current + self.batch_size) < self.n_samples:
            input = self.feature[self.current:self.current + self.batch_size]  # 形状 (1, n_features)
            target = self.label[self.current:self.current + self.batch_size]  # 形状 (1, 1)
            self.current += self.batch_size
            return input, target
        else:
            raise StopIteration

    def __len__(self):
        return self.n_samples

    def source(self):
        raise NotImplementedError


class RandomDataset(DataSet):
    def source(self):
        np.random.seed(42)
        # 生成线性回归算法的测试训练样本数据
        # X = np.random.randn(100, 3)  # 100 个样本，3 个特征
        # y = 2.5 * X[:, 0] - 1.3 * X[:, 1] + 0.7 * X[:, 2] + np.random.normal(0, 0.1, 100)

        # 生成逻辑回归算法的测试训练样本数据
        # 1. 生成特征矩阵 (100 个样本，3 个特征)
        X = np.random.randn(100, 3)  # 标准正态分布

        # 2. 定义真实权重和偏置（用于构造决策边界）
        true_weights = np.array([1.5, -0.8, 0.2])  # 权重向量 (3,)
        true_bias = 0.5  # 偏置项

        # 3. 计算线性组合并添加噪声
        z = np.dot(X, true_weights) + true_bias
        z += np.random.normal(0, 0.3, size=z.shape)  # 添加高斯噪声（标准差=1.0）

        # 4. 通过 Sigmoid 函数计算概率
        probabilities = 1 / (1 + np.exp(-z))

        # 5. 根据概率生成二分类标签 (0 或 1)
        Y = (probabilities >= 0.5).astype(int)
        Y = np.expand_dims(Y, axis=-1)
        return X, Y

class CSVDataset(DataSet):
    def source(self):
        data = np.genfromtxt(self.file_path, delimiter=',', dtype=np.float32)
        x = data[:, :-1]
        y = data[:, -1].reshape(-1, 1)
        return x, y

if __name__ == '__main__':
    my_train_dataset = CSVDataset('classify/train/data.csv')
    my_test_dataset = CSVDataset('classify/test/data.csv')
    my_val_dataset = CSVDataset('classify/val/data.csv')

    # my_dataset = RandomDataset(batch_size=2)
    for j in range(2):
        i = 0
        for x, y in my_train_dataset:
            print('i=', i)
            print(x, y)
            i += 1
        print('j=',j)