"""
AdaDelta

loss function: L(w1,w2)=0.5 * (w1-10)**2 + 5*(w2+30)**2
gradient: ((w1-10), 10*(w2+30))

w_initial: (0,0)
learning_rate: 0.1
"""

import numpy as np


def compute_gradient_adadelta(w1, w2, g_expectation_w1, g_expectation_w2, x_delta_expectation_w1,
                              x_delta_expectation_w2, parameter_rho=0.95, parameter_epsilon=1e-6):
    # 计算w的梯度
    g_w1 = w1 - 10
    g_w2 = 10 * (w2 + 30)

    # 计算历史梯度平方和的加权平均
    g_expectation_w1 = parameter_rho * g_expectation_w1 + (1 - parameter_rho) * np.square(g_w1)
    g_expectation_w2 = parameter_rho * g_expectation_w2 + (1 - parameter_rho) * np.square(g_w2)

    # 计算历史梯度均方根
    rms_g_w1 = np.sqrt(g_expectation_w1 + parameter_epsilon)
    rms_g_w2 = np.sqrt(g_expectation_w2 + parameter_epsilon)

    # 计算x_delta的均方根
    rms_x_delta_w1 = np.sqrt(x_delta_expectation_w1 + parameter_epsilon)
    rms_x_delta_w2 = np.sqrt(x_delta_expectation_w2 + parameter_epsilon)

    # 计算x_delta
    x_delta_w1 = (rms_x_delta_w1 / rms_g_w1) * g_w1
    x_delta_w2 = (rms_x_delta_w2 / rms_g_w2) * g_w2

    # 计算x_delta的平方和的加权平均，以便下一次迭代使用
    x_delta_expectation_w1 = parameter_rho * x_delta_expectation_w1 + (1 - parameter_rho) * np.square(x_delta_w1)
    x_delta_expectation_w2 = parameter_rho * x_delta_expectation_w2 + (1 - parameter_rho) * np.square(x_delta_w2)

    # 计算w的下一个位置
    w1 = w1 - x_delta_w1
    w2 = w2 - x_delta_w2

    return [w1, w2, g_w1, g_w2, g_expectation_w1, g_expectation_w2, x_delta_expectation_w1, x_delta_expectation_w2]


def compute_loss(w1, w2):
    loss = 0.5 * (w1 - 10) ** 2 + 5 * (w2 + 30) ** 2
    return loss


if __name__ == '__main__':
    w1 = w2 = 0
    g_expectation_w1 = g_expectation_w2 = 0
    x_delta_expectation_w1 = x_delta_expectation_w2 = 0
    parameter_rho = 0.95
    parameter_epsilon = 0.000001
    print("Adadelta算法，w初值:", (w1, w2), ", w最优值: (10,-30)", "rho=", parameter_rho, "epsilon=", parameter_epsilon)
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
