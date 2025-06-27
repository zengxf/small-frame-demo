"""
Adam (Adaptive Moment Estimation)

loss function: L(w) = (w - 20)^2 + 5
gradient: 2 * (w - 20)
"""

import numpy as np


# 定义损失函数和梯度
def loss_function(w):
    return (w - 20) ** 2 + 5


def gradient(w):
    return 2 * (w - 20)


# Adam 优化算法实现
def adam_optimizer(loss_func, grad_func, initial_w,
                   lr=0.01, betas=(0.9, 0.999), eps=1e-8, num_iters=1000
                   ):
    w = initial_w
    m = 0  # 一阶矩估计
    v = 0  # 二阶矩估计
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

        # # 打印训练信息（每100次打印一次）
        # if t % 100 == 0:
        loss = loss_func(w)
        print(f"Iteration {t}: w = {w:.6f}, loss = {loss:.6f}")

    print(f"\nOptimized w: {w:.6f}")
    print(f"True minimum at w = 20")
    return w


# 使用 Adam 优化器
if __name__ == "__main__":
    initial_w = 2.0  # 初始值
    optimized_w = adam_optimizer(loss_function, gradient, initial_w, lr=0.1, num_iters=900)
