"""
L(w0, w1) = 1/4 * ( (63 - (60 * w1 + w0))^2 + (65.2 - (62 * w1 + w0))^2 )

L'(w0) = −64.1 + 61w1 + w0
L'(w1) = −3911.2 + 3722w1 + 61w0
"""


def gradient_(w0, w1):
    gradient_w0 = -64.1 + 61 * w1 + w0
    gradient_w1 = -3911.2 + 3722 * w1 + 61 * w0
    # return np.array([-64.1 + 61 * w1 + w0, -3911.2 + 3722 * w1 + 61 * w0])
    # return np.array([gradient_w0, gradient_w1])
    # sum_ = (gradient_w0 ** 2 + gradient_w1 ** 2) ** 0.5
    # return np.array([gradient_w0 / sum_, gradient_w1 / sum_])  # 单位向量 s hat k
    return vector_unit(gradient_w0, gradient_w1)  # 单位向量 s hat k


def vector_unit(w0, w1):
    sum_ = (w0 ** 2 + w1 ** 2) ** 0.5
    return [w0 / sum_, w1 / sum_]
# print(vector_unit(3, 4))

def loss_(w0, w1):
    """
    L(w0, w1) = 1/4 * ( (63 - (60 * w1 + w0))^2 + (65.2 - (62 * w1 + w0))^2 )
    """
    return 1 / 4 * ((63 - (60 * w1 + w0)) ** 2 + (65.2 - (62 * w1 + w0)) ** 2)


# w0, w1, rate, times = 10, 10, 2, 5
# w0, w1, rate, times = 10, 10, 0.5, 20
w0, w1, rate, times = 10, 10, 1, 10
for step in range(times):
    grad = gradient_(w0, w1)
    # print(grad)
    w0 -= rate * grad[0]
    w1 -= rate * grad[1]
    loss = loss_(w0, w1)
    print(f"step={step + 1}, w0={w0:.2f}, w1={w1:.2f}, loss={loss:.2f}")
