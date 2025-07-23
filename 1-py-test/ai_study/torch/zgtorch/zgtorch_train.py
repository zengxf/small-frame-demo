"""
模型函数
1. 增加forward的方法
2. init，parameters方法转移到父类实现
3. sigmoid函数从损失函数转移到模型函数

数据集
1. 生成数据封装到DataSet类


"""
import argparse

import numpy as np
from data import CSVDataset, RandomDataset
from model import LinearRegression, LogicRegression
from optim import Adam
from loss import CrossEntropyLoss


# ------------------------------ 主程序 ------------------------------

def train():
    # 初始化组件
    model = LogicRegression(input_dim=2)  # 模型
    optimizer = Adam(model.parameters(), lr=0.0004)  # 优化器
    loss_fn = CrossEntropyLoss(model)  # 损失函数（绑定模型）

    train_file = r'data/classify/train/data.csv'
    batch_size = 8
    dataset = CSVDataset(train_file, batch_size)  # 数据集
    # dataset = RandomDataset()

    # 训练循环
    n_epochs = 200
    for epoch in range(n_epochs):
        total_loss = 0
        for input, target in dataset:
            # 前向传播
            optimizer.zero_grad()
            output = model(input)
            loss_value, loss_fn = loss_fn(output, target)  # 获取损失值和损失函数对象

            # 反向传播
            loss_fn.backward()

            # 参数更新
            optimizer.step()

            total_loss += loss_value

        # 打印损失
        print(f"Epoch {epoch + 1}/{n_epochs}, Loss: {total_loss / len(dataset):.4f}")

    # 查看训练后的参数
    print("\n训练后的参数:")
    print(f"权重 w: {model.w.data}")
    print(f"偏置 b: {model.b.data}")
    model.save(r'runs/weights.pkl')

def test():
    model = LogicRegression(input_dim=2)
    model.load(r'runs/weights.pkl')
    test_file = r'data/classify/test/data.csv'
    batch_size = 8
    dataset = CSVDataset(test_file, batch_size)  # 数据集

    total_loss = 0
    for input, target in dataset:
        # 前向传播
        output = model(input)
        print(output, target)



if __name__ == '__main__':
    '''
    # 定义可接受参数及其类型（会自动转换类型）
    parser = argparse.ArgumentParser()
   
    parser.add_argument("--epoch", type=int, default=50)
    args = parser.parse_args()
    print(args.epoch)

    '''

    np.random.seed(2025)
    # 训练
    train()

    # 验证
    # test()


