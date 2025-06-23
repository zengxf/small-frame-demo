'''
MOM (momentum)

L(w0, w1) = 1/4 * ( (63 - (60 * w1 + w0))^2 + (65.2 - (62 * w1 + w0))^2 )

L'(w0) = -64.1 + 61w1 + w0
L'(w1) = -3911.2 + 3722w1 + 61w0
'''

import numpy as np


def compute_gradient_momentum(w, v_last, rate, gamma):
    partial_w0 = -64.1 + 61 * w[1] + w[0]
    partial_w1 = -3911.2 + 3722 * w[1] + 61 * w[0]
    gradient = np.array([partial_w0, partial_w1])  # ∇

    # 计算本次变化量
    current_delta = rate * gradient  # ρ∇
    # 计算上次变化量的折扣
    gamma_v_last = gamma * v_last  # γv
    # 计算总的变化量，利用上次梯度来修正本次变化量，含大小和方向
    v_current = gamma_v_last + current_delta  # γv + ρ∇
    # 计算 w 的下一个位置
    w_new = w - v_current  # w - v

    return [w_new, gradient, v_current]


def loss_(w0, w1):
    return 1 / 4 * ((63 - (60 * w1 + w0)) ** 2 + (65.2 - (62 * w1 + w0)) ** 2)


# w0, w1, rate, times, gamma = 10, 10, 0.00005, 10, 0.5
# w0, w1, rate, times, gamma = 10, 10, 0.00004, 10, 0.5
# w0, w1, rate, times, gamma = 10, 10, 0.00002, 10, 0.5
w0, w1, rate, times, gamma = 10, 10, 0.00003, 15, 0.5

if __name__ == '__main__':
    W_INIT = np.array([w0, w1])

    w_current = W_INIT
    print(f"Momentum 算法，w 初值: {W_INIT}，学习率: {rate:>10f}, gamma: {gamma}")

    # momentum
    v_last = np.array([0, 0])
    for i in range(times):
        w, w_gradient, v_current = compute_gradient_momentum(w_current, v_last, rate, gamma)
        w_current = w
        v_last = v_current
        loss = loss_(w[0], w[1])
        print("step={0:>2d};  w={1};  loss={2:>10f};  grad={3};  v={4}"
              .format(i + 1, w, loss, w_gradient, v_current))
