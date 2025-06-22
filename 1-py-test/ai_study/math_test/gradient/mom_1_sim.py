'''
loss function: L(w) = (w - 20)^2 + 5
gradient: 2 * (w - 20)
'''

import numpy as np


def compute_gradient_momentum(w, v_last, learning_rate, gamma_factor):
    # 计算本次梯度
    w_gradient = 2 * (w - 20)  # ∇
    # 计算本次变化量
    current_delta = learning_rate * w_gradient  # ρ∇
    # 计算上次变化量的折扣
    last_delta_discount = gamma_factor * v_last  # γv
    # 计算总的变化量，利用上次梯度来修正本次变化量，含大小和方向
    v_current = last_delta_discount + current_delta  # γv + ρ∇
    # 计算 w 的下一个位置
    w_new = w - v_current  # w - v

    return [w_new, w_gradient, v_current]


def compute_loss(w):
    loss = np.square(w - 20) + 5
    return loss


if __name__ == '__main__':
    LEARNING_RATE = 0.1
    W_INIT = 2

    w_current = W_INIT
    print("Momentum 算法，w 初值:", W_INIT, "，w 最优值: 20", "，学习率:", LEARNING_RATE)

    # momentum
    v_last = 0
    gamma_factor = 0.5
    for i in range(20):
        w, w_gradient, v_current = compute_gradient_momentum(w_current, v_last, LEARNING_RATE, gamma_factor)
        w_current = w
        v_last = v_current
        loss = compute_loss(w)  # 函数 + 5，只能无限逼近 5
        print("step={0:>2d}  w={1:>10f}  loss={2:>10f}  grad={3:>10f}  delta={4:>10f}"
              .format(i + 1, w, loss, w_gradient, v_current))
