"""
AdaGrad (Adaptive Gradient)

L(w0, w1) = 1/4 * ( (63 - (60 * w1 + w0))^2 + (65.2 - (62 * w1 + w0))^2 )

L'(w0) = -64.1 + 61w1 + w0
L'(w1) = -3911.2 + 3722w1 + 61w0
"""

import numpy as np


def compute_gradient_adagrad(w0, w1, g_w0, g_w1, learning_rate):
    # 计算 w 的梯度
    w0_gradient = -64.1 + 61 * w1 + w0  # ∇
    w1_gradient = -3911.2 + 3722 * w1 + 61 * w0

    # 计算历史梯度平方的和
    g_w0 = g_w0 + np.square(w0_gradient)  # G(t-1) + ∇^2
    g_w1 = g_w1 + np.square(w1_gradient)

    # 计算 w 的下一个位置；lr_w* 相当于 w* 的动态学习率
    epsilon = 0.00000001
    lr_w0 = learning_rate / np.sqrt(g_w0 + epsilon)  # ρ / G^(1/2)
    lr_w1 = learning_rate / np.sqrt(g_w1 + epsilon)
    w0_new = w0 - lr_w0 * w0_gradient  # w - [ρ / G^(1/2)] * ∇
    w1_new = w1 - lr_w1 * w1_gradient

    return [w0_new, w1_new, g_w0, g_w1, w0_gradient, w1_gradient, lr_w0, lr_w1]


def loss_(w0, w1):
    return 1 / 4 * ((63 - (60 * w1 + w0)) ** 2 + (65.2 - (62 * w1 + w0)) ** 2)


# w0, w1, rate, times = 10, 10, 1, 100
# w0, w1, rate, times = 10, 10, 5, 20
w0, w1, rate, times = 10, 10, 10, 10

if __name__ == '__main__':
    w0_initial = w0
    w1_initial = w1
    g_w0 = g_w1 = 0

    print("AdaGrad 算法，w 初值:", (w0_initial, w1_initial), "，w 最优值: (10, -30)", "，学习率:", rate)
    for i in range(times):
        w0, w1, g_w0, g_w1, w0_gradient, w1_gradient, lr_w0, lr_w1 = \
            compute_gradient_adagrad(w0, w1, g_w0, g_w1, rate)
        loss = loss_(w0, w1)
        print("step={0:0>2d}  (w0, w2)=({1:>10f}, {2:>10f})  loss={3:>10f}  (w1_gradient, lr_w1)=({4:>10f}, {5:>9f})  "
              "(w2_gradient, lr_w2)=({6:>10f}, {7:>9f})"
              .format(i + 1, w0, w1, loss, w0_gradient, lr_w0, w1_gradient, lr_w1))
