import numpy as np
import cv2

info = {
    "reprojection_error": 2.001,
    "camera_matrix": [
        [1830.60467, 0.0, 1867.32501],
        [0.0, 1829.48682, 1128.58503],
        [0.0, 0.0, 1.0]
    ],
    "dist_coeffs": [-0.01858246, 0.06311147, -0.00019638, -0.00354312, -0.08480418]
}


def undistort_image(image,
                    camera_matrix=np.array(info.get("camera_matrix")),
                    dist_coeffs=np.array(info.get("dist_coeffs"))):
    h, w = image.shape[:2]

    # 保留全部视野
    new_camera_matrix, roi = cv2.getOptimalNewCameraMatrix(
        camera_matrix, dist_coeffs, (w, h), alpha=1, centerPrincipalPoint=1
    )

    # 更精确的像素映射（推荐）
    mapx, mapy = cv2.initUndistortRectifyMap(
        camera_matrix, dist_coeffs, None, new_camera_matrix, (w, h), cv2.CV_32FC1
    )
    undistorted = cv2.remap(image, mapx, mapy, interpolation=cv2.INTER_LINEAR)

    # 不裁剪，保留全部图像
    return undistorted

if __name__ == "__main__":
    img = cv2.imread(r"F:\2025\py_do2025\AIFaCai2025\5_14_25mm_ji.jpg")
    img = undistort_image(img)
    cv2.imwrite("444.jpg",img)