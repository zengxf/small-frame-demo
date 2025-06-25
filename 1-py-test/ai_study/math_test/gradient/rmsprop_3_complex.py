"""
RMSprop (Root Mean Squared Propagation)

L(w0, w1) = 1/4 * ( (63 - (60 * w1 + w0))^2 + (65.2 - (62 * w1 + w0))^2 )

L'(w0) = -64.1 + 61w1 + w0
L'(w1) = -3911.2 + 3722w1 + 61w0

w_initial: (10, 10)
learning_rate: 0.01
"""

import numpy as np


# 定义损失函数
def loss(w0, w1):
    return 1 / 4 * ((63 - (60 * w1 + w0)) ** 2 + (65.2 - (62 * w1 + w0)) ** 2)


# 定义梯度
def gradient(w0, w1):
    grad_w0 = -64.1 + 61 * w1 + w0
    grad_w1 = -3911.2 + 3722 * w1 + 61 * w0
    return np.array([grad_w0, grad_w1])


# RMSprop optimizer
def rmsprop(lr=0.01, gamma=0.9, eps=1e-8, num_iters=1000, print_every=10):
    # 初始参数
    w = np.array([10.0, 10.0])  # 初始 w1=0, w2=0
    Eg2 = 1e-6  # 初始滑动平均平方梯度
    # Eg2 = 0  # 初始滑动平均平方梯度

    for i in range(num_iters):
        grad = gradient(w[0], w[1])  # g_t
        Eg2 = gamma * Eg2 + (1 - gamma) * grad ** 2  # E[g^2]t
        update = lr / np.sqrt(Eg2 + eps) * grad  # (ρ / √E[g^2]t) * g_t
        w -= update

        if (i + 1) % print_every == 0:
            l = loss(w[0], w[1])
            print(f"Iter {i + 1}: w = {w}, Loss = {l:.6f}")

    return w


# 运行优化器
optimal_weights = rmsprop(lr=0.1, num_iters=240)
print("Optimized weights:", optimal_weights)
