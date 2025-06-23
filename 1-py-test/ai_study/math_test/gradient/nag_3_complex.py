'''
NAG (Nesterov Accelerated Gradient)

L(w0, w1) = 1/4 * ( (63 - (60 * w1 + w0))^2 + (65.2 - (62 * w1 + w0))^2 )

L'(w0) = -64.1 + 61w1 + w0
L'(w1) = -3911.2 + 3722w1 + 61w0
'''

import numpy as np


def compute_gradient_nag(w, v_last, learning_rate, gamma_factor):
    # 计算上次变化量的折扣
    gamma_v = gamma_factor * v_last  # γv

    # 预估 w 下一次的位置
    w_estimate_next = w - gamma_v  # w - γv
    w_e = w_estimate_next

    # 预估下一次的梯度
    partial_w0 = -64.1 + 61 * w_e[1] + w_e[0]
    partial_w1 = -3911.2 + 3722 * w_e[1] + 61 * w_e[0]
    gradient = np.array([partial_w0, partial_w1])  # ∇

    # 计算本次变化量
    current_delta = learning_rate * gradient  # ρ∇

    # 计算总变化量，利用上一次梯度和下一次梯度来修正本次变化量，含大小和方向
    v_current = gamma_v + current_delta

    # 计算 w 下一次位置
    w_new = w - v_current

    return [w_new, gradient, v_current]


def loss_(w0, w1):
    return 1 / 4 * ((63 - (60 * w1 + w0)) ** 2 + (65.2 - (62 * w1 + w0)) ** 2)


# w0, w1, gamma, rate, times = 10, 10, 0.5, 0.00005, 10
w0, w1, gamma, rate, times = 10, 10, 0.5, 0.00004, 15
# w0, w1, gamma, rate, times = 10, 10, 0.5, 0.00003, 20
# w0, w1, gamma, rate, times = 10, 10, 0.5, 0.00002, 35

if __name__ == '__main__':
    W_INIT = np.array([w0, w1])

    w_current = W_INIT
    print(f"NAG(Nesterov Accelerated Gradient)算法，w 初值: {W_INIT}，学习率: {rate:>10f}, gamma: {gamma}")

    # NAG
    v_last = np.zeros(2)
    for i in range(times):
        new_w, w_gradient, v_current = compute_gradient_nag(w_current, v_last, rate, gamma)
        w_current = new_w
        v_last = v_current
        loss = loss_(new_w[0], new_w[1])
        print(
            "step={0:0>2d};  w={1};  loss={2:>10f};  grad={3};  delta={4}"
            .format(i + 1, new_w, loss, w_gradient, v_current))
