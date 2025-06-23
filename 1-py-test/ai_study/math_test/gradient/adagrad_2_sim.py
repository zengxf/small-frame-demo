"""
AdaGrad (Adaptive Gradient)

loss function: L(w1, w2) = 0.5 * (w1 - 10)**2  +  5 * (w2 + 30)**2
gradient: ((w1 - 10),  10 * (w2 + 30))

w_initial: (0, 0)
learning_rate: 0.1
"""

import numpy as np


def compute_gradient_adagrad(w1, w2, g_w1, g_w2, learning_rate):
    # 计算 w 的梯度
    w1_gradient = w1 - 20
    w2_gradient = 10 * (w2 + 30)

    # 计算历史梯度平方的和
    g_w1 = g_w1 + np.square(w1_gradient)
    g_w2 = g_w2 + np.square(w2_gradient)

    # 计算 w 的下一个位置
    epsilon = 0.00000001
    lr_w1 = learning_rate / np.sqrt(g_w1 + epsilon)
    lr_w2 = learning_rate / np.sqrt(g_w2 + epsilon)
    w1_new = w1 - lr_w1 * w1_gradient
    w2_new = w2 - lr_w2 * w2_gradient

    return [w1_new, w2_new, g_w1, g_w2, w1_gradient, w2_gradient, lr_w1, lr_w2]


def compute_loss(w1, w2):
    loss = 0.5 * (w1 - 10) ** 2 + 5 * (w2 + 30) ** 2
    return loss


if __name__ == '__main__':
    # LEARNING_RATE, times = 0.1, 100000
    # LEARNING_RATE, times = 1, 1200
    # LEARNING_RATE, times = 10, 40  # rate 要大些
    LEARNING_RATE, times = 15, 20  # rate 要大些
    w1_initial = 10
    w2_initial = 10

    w1 = w1_initial
    w2 = w2_initial
    g_w1 = g_w2 = 0

    print("AdaGrad 算法，w 初值:", (w1_initial, w2_initial), "，w 最优值: (10, -30)", "，学习率:", LEARNING_RATE)
    for i in range(times):
        w1, w2, g_w1, g_w2, w1_gradient, w2_gradient, lr_w1, lr_w2 = \
            compute_gradient_adagrad(w1, w2, g_w1, g_w2, LEARNING_RATE)
        loss = compute_loss(w1, w2)
        print("step={0:0>2d}  (w1, w2)=({1:>10f}, {2:>10f})  loss={3:>10f}  (w1_gradient, lr_w1)=({4:>10f}, {5:>9f})  "
              "(w2_gradient, lr_w2)=({6:>10f}, {7:>9f})"
              .format(i + 1, w1, w2, loss, w1_gradient, lr_w1, w2_gradient, lr_w2))
