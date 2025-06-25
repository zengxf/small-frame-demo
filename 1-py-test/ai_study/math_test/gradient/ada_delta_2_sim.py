"""
AdaDelta (Adaptive Delta)

loss function: L(w1,w2)=0.5 * (w1-10)**2 + 5*(w2+30)**2
gradient: ((w1-10), 10*(w2+30))

w_initial: (0,0)
learning_rate: 0.1
"""

import numpy as np


# exp: expectation
def compute_gradient_adadelta(w1, w2, g2_exp_w1, g2_exp_w2, x2_delta_exp_w1,
                              x2_delta_exp_w2, rho=0.95, eps=1e-6):
    # 计算 w 的梯度 gt
    g_w1 = w1 - 10  # ∇
    g_w2 = 10 * (w2 + 30)

    # 计算历史梯度平方和的加权平均 E[g^2]t
    # (p): pre 上一次;  (c): cur 当前
    g2_exp_w1 = rho * g2_exp_w1 + (1 - rho) * np.square(g_w1)  # ρ*E[g(p)^2] + (1-ρ)*g(c)^2
    g2_exp_w2 = rho * g2_exp_w2 + (1 - rho) * np.square(g_w2)

    # 计算历史梯度均方根 RMS[g]t
    rms_g_w1 = np.sqrt(g2_exp_w1 + eps)  # √(E[g^2] + ε)
    rms_g_w2 = np.sqrt(g2_exp_w2 + eps)

    # 计算 x_delta 的均方根 RMS[Δx]t-1
    rms_x_delta_w1 = np.sqrt(x2_delta_exp_w1 + eps)  # √(E[Δx^2] + ε)
    rms_x_delta_w2 = np.sqrt(x2_delta_exp_w2 + eps)

    # 计算 x_delta (Δx)
    x_delta_w1 = (rms_x_delta_w1 / rms_g_w1) * g_w1  # RMS[Δx] / RMS[g] * g
    x_delta_w2 = (rms_x_delta_w2 / rms_g_w2) * g_w2

    # 计算 x_delta 的平方和的加权平均，以便下一次迭代使用  E[Δx^2]t
    x2_delta_exp_w1 = rho * x2_delta_exp_w1 + (1 - rho) * np.square(x_delta_w1)  # ρ*E[Δx(p)^2] + (1-ρ)*Δx(c)^2
    x2_delta_exp_w2 = rho * x2_delta_exp_w2 + (1 - rho) * np.square(x_delta_w2)

    # 计算 w 的下一个位置
    w1 = w1 - x_delta_w1  # - Δx
    w2 = w2 - x_delta_w2

    return [w1, w2, g_w1, g_w2, g2_exp_w1, g2_exp_w2, x2_delta_exp_w1, x2_delta_exp_w2]


def compute_loss(w1, w2):
    loss = 0.5 * (w1 - 10) ** 2 + 5 * (w2 + 30) ** 2
    return loss


if __name__ == '__main__':
    w1 = w2 = 0
    g_expectation_w1 = g_expectation_w2 = 0
    x_delta_expectation_w1 = x_delta_expectation_w2 = 0
    parameter_rho = 0.95
    parameter_epsilon = 0.000001
    print("Adadelta 算法，w 初值:", (w1, w2), ", w 最优值: (10,-30), rho=", parameter_rho, ", epsilon=",
          parameter_epsilon)
    for i in range(5000):
        w1, \
            w2, \
            g_w1, \
            g_w2, \
            g_expectation_w1, \
            g_expectation_w2, \
            x_delta_expectation_w1, \
            x_delta_expectation_w2 = compute_gradient_adadelta(w1,
                                                               w2,
                                                               g_expectation_w1,
                                                               g_expectation_w2,
                                                               x_delta_expectation_w1,
                                                               x_delta_expectation_w2,
                                                               parameter_rho,
                                                               parameter_epsilon)

        loss = compute_loss(w1, w2)
        print("step={0:0>2d}  (w1,w2)=({1:> .18f},{2:> .18f})  loss={3:> .18f} (g_w1,g_w2)=({4:> .18f},{5:> .18f})"
              .format(i + 1, w1, w2, loss, g_w1, g_w2))
