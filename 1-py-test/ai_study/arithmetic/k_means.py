"""
ref: https://www.kimi.com/chat/d2fa4853v89o75vquih0
"""

import numpy as np
from numpy.linalg import norm

def kmeans(X, k, max_iters=100, tol=1e-4, seed=None):
    """
    X: (n_samples, n_features) 的 ndarray
    k: 簇数
    max_iters, tol: 停止条件
    return: (labels, centers)
    """
    n, d = X.shape
    rng = np.random.default_rng(seed)

    # 1. k-means++ 初始化
    centers = [X[rng.integers(n)]]
    for _ in range(1, k):
        dist2 = np.min(np.sum((X[:, None, :] - np.array(centers)[None, :, :])**2, axis=2), axis=1)
        prob = dist2 / dist2.sum()
        centers.append(X[rng.choice(n, p=prob)])
    centers = np.array(centers)

    for it in range(max_iters):
        # 2. Assignment
        distances = np.linalg.norm(X[:, None] - centers[None, :], axis=2)  # (n, k)
        labels = np.argmin(distances, axis=1)

        # 3. Update
        new_centers = np.array([X[labels == j].mean(axis=0) if np.sum(labels == j) else centers[j]
                                for j in range(k)])
        shift = norm(new_centers - centers)
        centers = new_centers
        if shift < tol:
            print(f"Converged at iteration {it}")
            break
    else:
        print("Reach max_iters")
    return labels, centers


# ------------------ Demo ------------------
if __name__ == "__main__":
    import matplotlib.pyplot as plt

    # 生成 3 个二维高斯簇
    np.random.seed(42)
    X = np.vstack([np.random.randn(80, 2) + [2, 2],
                   np.random.randn(80, 2) + [-2, -2],
                   np.random.randn(80, 2) + [2, -2],
                   np.random.randn(80, 2) + [-2, 2]])

    labels, centers = kmeans(X, k=3, seed=1)

    # 画图
    plt.scatter(X[:, 0], X[:, 1], c=labels, s=30, cmap='Set1')
    plt.scatter(centers[:, 0], centers[:, 1], c='black', marker='x', s=150)
    plt.title("k-means (k=3) result")
    plt.show()