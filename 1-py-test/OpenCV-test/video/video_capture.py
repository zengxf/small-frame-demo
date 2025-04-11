"""
实时摄像头捕获
"""

import cv2

cap = cv2.VideoCapture(0)  # 0 表示默认摄像头
while True:
    ret, frame = cap.read()
    if not ret:
        break

    print("-------------------------------")
    print('抓取显示', ret)
    # print(frame)  # 多维数组
    print('key: ', ord('q'))
    cv2.imshow('Live Video', frame)

    if cv2.waitKey(1) & 0xFF == ord('q'):  # 按 q 退出
        break

cap.release()
cv2.destroyAllWindows()
