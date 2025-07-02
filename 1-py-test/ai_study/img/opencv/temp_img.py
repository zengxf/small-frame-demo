import cv2
import time
import uuid

file_name = r"D:/Data/Test/img/test-s1.mp4"
rtsp_url = r"http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8"
# 创建一个视频流对象
video = cv2.VideoCapture(file_name)  # 20s * 33.97帧/s
# video = cv2.VideoCapture(rtsp_url)
i = 0
while True:
    s = time.time()
    ret, frame = video.read()  # 读 1 帧 (直接读，不会等，很快)
    print(f'读取 {i} 帧，耗时: {time.time() - s} s')
    # persons1 =  检查是否有人的函数(frame) # 每个帧都检测

    # 抽帧检测
    # if i % 6 == 0:
    #   persons2 =  检查是否有人的函数(frame)
    #   cv2.imwrite(f'./imgs/{str(uuid.uuid4())}.jpg', frame) # 保存图片

    if ret:  # 是否读成功
        cv2.imshow('video', frame)
        cv2.waitKey(1)
        i += 1
    else:
        # 已读完
        print(f'已读完，i: {i}')
        cv2.waitKey(0)
        break
    # if i == 30:
    #     i = 0