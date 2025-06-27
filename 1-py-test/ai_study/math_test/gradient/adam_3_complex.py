"""
Adam (Adaptive Moment Estimation)

L(w0, w1) = 1/4 * ( (63 - (60 * w1 + w0))^2 + (65.2 - (62 * w1 + w0))^2 )

L'(w0) = -64.1 + 61w1 + w0
L'(w1) = -3911.2 + 3722w1 + 61w0
"""

import numpy as np


# 定义损失函数和梯度
def loss_function(w):
    w0 = w[0]
    w1 = w[1]
    ls = 1 / 4 * ((63 - (60 * w1 + w0)) ** 2 + (65.2 - (62 * w1 + w0)) ** 2)
    return ls


def gradient(w):
    w0 = w[0]
    w1 = w[1]
    g0 = -64.1 + 61 * w1 + w0
    g1 = -3911.2 + 3722 * w1 + 61 * w0
    return np.array([g0, g1])


# Adam 优化算法实现
def adam_optimizer(loss_func, grad_func, initial_w,
                   lr=0.01, betas=(0.9, 0.999), eps=1e-8, num_iters=1000
                   ):
    w = initial_w
    m = np.zeros_like(initial_w)  # 一阶矩估计
    v = np.zeros_like(initial_w)  # 二阶矩估计
    beta1, beta2 = betas

    print(f"Starting optimization from w = {w}")

    for t in range(1, num_iters + 1):
        g_t = grad_func(w)  # 计算当前梯度

        # 更新一阶矩估计和二阶矩估计
        m = beta1 * m + (1 - beta1) * g_t
        v = beta2 * v + (1 - beta2) * g_t ** 2

        # 偏差校正
        m_hat = m / (1 - beta1 ** t)
        v_hat = v / (1 - beta2 ** t)

        # 参数更新
        w = w - lr * m_hat / (np.sqrt(v_hat) + eps)

        # 打印训练信息（每 50 次打印一次）
        # if t % 50 == 0:
        loss = loss_func(w)
        print(f"Iteration {t}: w = {w}, loss = {loss:.6f}")

    print(f"\nOptimized w: {w}")
    return w


# 使用 Adam 优化器
if __name__ == "__main__":
    initial_w = np.array([2, 2])  # 初始值
    optimized_w = adam_optimizer(loss_function, gradient, initial_w, lr=0.1, num_iters=300)
