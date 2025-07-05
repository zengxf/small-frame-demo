# 第2章/machineLearn/一元线性回归梯度下降求解.py
import matplotlib.pyplot as plt


# 预测函数
def prediction(w, x, b):
    return w * x + b


# 损失函数
def sum_loss(X, Y, w, b):
    total_loss = 0
    for x, y in zip(X, Y):
        total_loss += (y - prediction(w, x, b)) ** 2
    return 1 / (2 * len(X)) * total_loss


# w的梯度，使用了全量梯度
def sum_gradient_w(X, Y, w, b):
    total_w = 0
    for x, y in zip(X, Y):
        total_w += (prediction(w, x, b) - y) * x
    return 1 / len(X) * total_w


# b的梯度，使用了全量梯度
def sum_gradient_b(X, Y, w, b):
    total_b = 0
    for x, y in zip(X, Y):
        total_b += (prediction(w, x, b) - y) * 1
    return 1 / len(X) * total_b
def plt_gradient_linear_process(X, Y, result_wb):
    # 用来正常显示中文标签
    plt.rcParams['font.sans-serif'] = ['SimHei']
    plt.rcParams['axes.unicode_minus'] = False
    # 将x周的刻度线方向设置向内
    plt.rcParams['xtick.direction'] = 'in'
    # 将y轴的刻度方向设置向内
    plt.rcParams['ytick.direction'] = 'in'
    plt.scatter(X, Y)
    length = len(result_wb)
    num = int(length * 0.2)
    mid_wb = result_wb[num]
    last_wb = result_wb[-1]
    plt.plot(X, [mid_wb[0] * x + mid_wb[1] for x in X], label='学习第{}次拟合直线'.format(num),linestyle='--')
    plt.plot(X, [0.4645 * x + 35.97 for x in X], label='最佳拟合直线')
    plt.plot(X, [last_wb[0] * x + last_wb[1] for x in X], label='学习第{}次拟合直线'.format(length),linestyle=':')
    # plt.title("父亲身高x（时）与儿子身高y（时）的关系\n梯度下降线性拟合过程")
    plt.xlabel('父亲身高x（时）')
    plt.ylabel('儿子身高y（时）')
    plt.legend(loc='upper left')
    plt.text(x=69,
             y=70,
             s=f'temp_w={init_wb[0]}\n'
               f'temp_b={init_wb[1]}\n'
               f'learning={learning}\n'
               f'epoch={epoch}\n'
               f'loss={round(loss, 4)}',
             fontdict=dict(fontsize=8, family='monospace', ),  # 字体属性字典
             # 添加文字背景色
             bbox={'facecolor': '#74C476',  # 填充色
                   'edgecolor': 'b',  # 外框色
                   'alpha': 0.8,  # 框透明度
                   'pad': 0.9,  # 本文与框周围距离
                   }
             )
    plt.savefig('loss_lr.png')
    plt.show()

if __name__ == "__main__":
    # import numpy
    # numpy.random.uniform()
    # X, 归一化。uniform() 随机值，但是满足正态分布。

    epoch = 21  # 迭代次数
    init_wb = 1, 40  # 初始参数值 迁移学习
    temp_w, temp_b = init_wb[0], init_wb[1]
    learning = 0.0001  # 学习率 1e-3, 1e-4,1e-5,1e-6[*1.0, *3.0, *5.0], epoch = 200,最好前100左右1e-3, 后再按%20 *0.1
    X = [60, 62, 64, 65, 66, 67, 68, 70, 72, 74]
    Y = [63.6, 65.2, 66, 65.5, 66.9, 67.1, 67.4, 68.3, 70.1, 70]
    result_wb = []  # 存储过程中的梯度
    for item in range(epoch):
        # 梯度下降
        w = temp_w - learning * sum_gradient_w(X, Y, temp_w, temp_b)
        b = temp_b - learning * sum_gradient_b(X, Y, temp_w, temp_b)
        loss = sum_loss(X, Y, w, b)
        if item % 5 == 0:
            print('第{}次梯度,(w,b)参数为({:6f},{:6f}),总损失{:6f}'.format(item + 1, w, b, loss))
        # 更新后再赋值给temp，这样能保证w、b同时更新
        temp_w = w
        temp_b = b
        result_wb.append([temp_w, temp_b])
    plt_gradient_linear_process(X, Y, result_wb)
