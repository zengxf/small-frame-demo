# -*- coding: UTF-8 -*-
'''
loss function: L(w) = (w - 20)^2 + 5
gradient: 2 * (w - 20)
'''

import numpy as np


def compute_gradient_sgd(w, learning_rate):
    w_gradient = 2 * (w - 20)
    w_new = w - learning_rate * w_gradient
    return [w_new, w_gradient]


def compute_loss(w):
    # loss = np.square(w - 20) + 5
    loss = (w - 20) ** 2 + 5
    return loss


if __name__ == '__main__':
    LEARNING_RATE = 0.1
    W_INIT = 2

    w_current = W_INIT
    print("SGD算法，w初值:", W_INIT, ", w最优点: 20", ", 学习率:", LEARNING_RATE)

    # sgd
    for i in range(20):
        w, w_gradient = compute_gradient_sgd(w_current, LEARNING_RATE)
        loss = compute_loss(w)
        w_current = w
        print("step={0:0>2d}  w={1:>10f}  loss={2:>10f}  grad={3:>10f}  delta={4:>20.18f}"
              .format(i + 1, w, loss, w_gradient, LEARNING_RATE * w_gradient)
              )
