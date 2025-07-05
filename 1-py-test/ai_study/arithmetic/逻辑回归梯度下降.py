# 第2章/machineLearn/逻辑回归梯度下降.py
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

plt.rcParams['font.sans-serif'] = ['SimHei']
plt.rcParams['axes.unicode_minus'] = False
# 将x周的刻度线方向设置向内
plt.rcParams['xtick.direction'] = 'in'
# 将y轴的刻度方向设置向内
plt.rcParams['ytick.direction'] = 'in'


# 将所有数据展示在图表中
def describe_scatter(x1, x2):
    plt.scatter(x1[:, 0], x1[:, 1], label='未录取分数',marker='s')
    plt.scatter(x2[:, 0], x2[:, 1], label='录取分数')
    plt.xlabel(u'语文分数')
    plt.ylabel(u'数学分数')
    # plt.title("某学校录取结果分布")
    plt.savefig('某学校录取结果分布')
    plt.legend(loc='upper right')
    plt.legend()
    plt.show()


def describe_sigmoid():
    fig, ax = plt.subplots()
    ax.spines['right'].set_visible(False)
    # ax.spines['top'].set_visible(False)
    ax.xaxis.set_ticks_position('bottom')
    ax.spines['bottom'].set_position(('data', 0))
    ax.yaxis.set_ticks_position('left')
    ax.spines['left'].set_position(('data', 0))

    x = list(range(-10, 10))
    y = [1 / (1 + np.exp(-z)) for z in x]
    plt.plot(x, y, label='sigmoid函数')

    plt.scatter(0, 0.5, color='red')
    plt.text(0.5, 0.5, '0.5', color='red')
    plt.xlabel(u'x')
    plt.ylabel(u'y')
    # plt.title("Sigmoid函数值域分布")
    plt.savefig('Sigmoid函数值域分布')
    plt.legend(loc='upper right')
    plt.legend()
    plt.show()


# 处理数据，然后展示到图表中
def describe_plt_data():
    data = pd.read_csv('某学校录取结果.csv', encoding='gbk')
    ok_result = []
    fail_result = []
    for index, row in enumerate(data['录取结果']):
        if row == 0:  # 根据录取结果来区分内容
            fail_result.append(data.iloc[index, :2].values)
        else:
            ok_result.append(data.iloc[index, :2].values)
    describe_scatter(np.array(fail_result), np.array(ok_result))


# 数据预处理
def data_preprocessing(path, frac=0.8):
    # 读取数据
    data = pd.read_csv(path, encoding='gbk')
    # 打乱数据并取80%的内容
    data = data.sample(frac=frac).reset_index(drop=True)
    data.insert(0, '偏置顶', 1)
    return data

# Sigmoid()函数
def sigmoid(z):
    return 1 / (1 + np.exp(-z))

# 逻辑回归的损失函数
def cost_loss(X, Y, theta):
    # X为样本,Y为真实值
    # 根据权重theta得到预测值
    pre_Y = sigmoid(np.matmul(X, theta))
    # 损失计算参考公式(2-37)
    result = -np.sum(Y * np.log(pre_Y) +
                     (1 - Y) * np.log(1 - pre_Y)) / len(Y)
    return result


def describe_plt_loss(epoch, loss_list):
    plt.plot(range(epoch), loss_list, color='red', label='损失')
    plt.xlabel(u'迭代次数')
    plt.ylabel(u'损失')
    # plt.title("逻辑回归迭代次数与损失变化")
    plt.savefig('逻辑回归迭代次数与损失变化')
    plt.legend(loc='upper right')
    plt.legend()
    plt.show()


# 根据获得的结果进行图形化验证
def verfiy_result(final_theta, theta2):
    x1 = np.arange(100, step=1)
    x2 = -(final_theta[0] + x1 * final_theta[1]) / final_theta[2]
    data = pd.read_csv('某学校录取结果.csv', encoding='gbk')
    # 图型化展示其数据
    positive = data[data['录取结果'] == 1]  # 1
    negetive = data[data['录取结果'] == 0]  # 0
    fig, ax = plt.subplots(figsize=(8, 5))
    ax.scatter(positive['语文'], positive['数学'], label='未录取分数',marker='s')
    ax.scatter(negetive['语文'], negetive['数学'], label='录取分数')
    ax.plot(x1, x2, label='最后决策边界')
    x3 = -(theta2[0] + x1 * theta2[1]) / theta2[2]
    ax.plot(x1, x3, label='中间决策边界')
    ax.set_xlabel('语文')
    ax.set_ylabel('数学')
    # ax.set_title('某学校录取结果参数拟合过程')
    plt.legend(loc='upper right')
    plt.show()


if __name__ == "__main__":
    # describe_plt_data()
    describe_sigmoid()
    data = data_preprocessing('某学校录取结果.csv')

    X = data.iloc[:, :3].values
    Y = np.reshape(data.iloc[:, -1].values, [-1, 1])
    # 设置学习率
    alpha = 1e-5
    # 设置迭代次数
    epoch = 250000
    # 初始权重
    theta = np.zeros([3, 1])
    loss_list = []
    theta_list = []
    for i in range(epoch):
        gradient = np.matmul(X.T, sigmoid(np.matmul(X, theta)) - Y)
        # 梯度下降
        theta = theta - alpha * gradient
        # 求损失
        loss = cost_loss(X, Y, theta)
        if i % 20 == 0 or i == 99:
            print('第{}次,损失={}'.format(i + 1, loss))
        loss_list.append(loss)
        theta_list.append(theta)
    # 将loss进行图像绘制
    describe_plt_loss(epoch, loss_list)
    # 某学生的考试成绩
    pre_x = np.array([[1, 80, 60]])
    # 获取最后1次权重值来预测学生的录取结果
    result = sigmoid(np.matmul(pre_x, theta))
    # 阈值自定义
    threshold = 0.5
    print("该学生被录取") if np.squeeze(result) > threshold else print('该学生未录取')
    # 图型化验证最后1次的参数的分隔结果
    print('last_theta:', theta)
    verfiy_result(theta, theta_list[int(len(theta_list) * 0.1)])
