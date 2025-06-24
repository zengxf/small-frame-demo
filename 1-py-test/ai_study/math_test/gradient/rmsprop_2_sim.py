"""
RMSprop (Root Mean Squared Propagation)

loss function: L(w1, w2) = 0.5 * (w1 - 10)**2  +  5 * (w2 + 30)**2
gradient: ((w1 - 10),  10 * (w2 + 30))

w_initial: (0, 0)
learning_rate: 0.01
"""

import numpy as np


# 定义损失函数
def loss(w1, w2):
    return 0.5 * (w1 - 10) ** 2 + 5 * (w2 + 30) ** 2


# 定义梯度
def gradient(w1, w2):
    grad_w1 = (w1 - 10)
    grad_w2 = 10 * (w2 + 30)
    return np.array([grad_w1, grad_w2])


# RMSprop optimizer
def rmsprop(lr=0.01, gamma=0.9, eps=1e-8, num_iters=1000, print_every=10):
    # 初始参数
    w = np.array([0.0, 0.0])  # 初始 w1=0, w2=0
    Eg2 = 1e-6  # 初始滑动平均平方梯度
    Eg2 = 0  # 初始滑动平均平方梯度

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
optimal_weights = rmsprop(lr=0.1, num_iters=400)
print("Optimized weights:", optimal_weights)
