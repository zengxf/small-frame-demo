"""
AdaDelta (Adaptive Delta)

loss function: L(w1,w2)=0.5 * (w1-10)**2 + 5*(w2+30)**2
gradient: ((w1-10), 10*(w2+30))

w_initial: (0,0)
learning_rate: 0.1
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


# AdaDelta 优化器实现
# x0 为 w 参数数组
def adadelta(grad, loss_func, x0, rho=0.95, eps=1e-6, num_iters=1000):
    x = np.array(x0, dtype=float)
    dim = len(x)

    # 初始化历史累积项和参数更新项
    E_g2 = np.zeros(dim)  # 梯度平方的滑动平均  E[g^2]
    E_delta_x2 = np.zeros(dim)  # 参数更新平方的滑动平均  E[Δx^2]
    delta_x = None  # Δx

    trajectory = [x.copy()]

    for t in range(num_iters):
        # 计算当前梯度
        g = grad(x[0], x[1])  # ∇

        # 更新梯度平方的滑动平均
        E_g2 = rho * E_g2 + (1 - rho) * g ** 2  # E[g^2]

        # 计算 RMS 值
        rms_g = np.sqrt(E_g2 + eps)  # RMS[g]

        # 计算参数更新
        delta_x = - (np.sqrt(E_delta_x2 + eps) / rms_g) * g  # Δx

        # 更新参数
        x += delta_x  # + Δx
        trajectory.append(x.copy())

        # 更新参数：更新平方的滑动平均
        E_delta_x2 = rho * E_delta_x2 + (1 - rho) * delta_x ** 2  # E[Δx^2]

        if t % 100 == 0:
            print(f"Iteration {t}: w = {x}, Loss = {loss_func(x[0], x[1])}")

    return x, trajectory


# 初始参数
w_initial = [0.0, 0.0]

# 运行 AdaDelta
optimal_weights, _ = adadelta(gradient, loss, w_initial, num_iters=5000)

print("\nOptimized weights:", optimal_weights)
print("Expected minimum at (10, -30)")
