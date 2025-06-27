import cv2
import random
import numpy as np

"""
    连续的图像，就是视频
    怎么评估视频的连贯性：帧率，1秒30张图片，30fps。
    人的眼晴，最低15fps。
    15fps, 20fps, 30fps
    
    分辨率的问题
    #      width x height
    200万像素：1920×1080（或1600×1200）  *****
    300万像素：2048×1536
    400万像素：2560×1440（或2304×1728）
    500万像素：2592×1944         *****
    800万像素：3264×2448         *****
    1200万像素：4000×3000
    1300万像素：4208×3120
    2000万像素：5120×3840        *****
    
    工业相机通常是黑白色
    
    看视频的角度
    720p：1280×720
    1080p：1920×1080
    4K：3840×2160
    8K：7680×4320
    
    红外相机 分辨率 256x192  价格高  可视距离短
    
    双目相机 3D视觉/深度相机
    有Z轴，深度信息
    
    
    
"""


def show(img, title=''):
    cv2.imshow(title, img)
    # 等待在图中按任意键
    cv2.waitKey(0)


"""


# opencv可以操作视频流
# 视频流的来源：
# 1. 本地相机/USB相机（几乎无延迟）
  2. 本地的视频文件 （几乎无延迟）
  3. 也可以通过rtsp协议来实时读取（有一定的延迟）
     通过网络流，可以边缓冲边播放视频流（假设，一次缓冲10fps的内容，播放5fps的内容）
     
     几乎所有光学摄像头都支持RTSP协议。可以通过RTSP协议将摄像头（IPC  IP Camera 网络摄像机）
     
     视频流直接从IPC中拿（用RTSP协议去摄像头拿视频流）
     
  背景：给的数据源是视频流，但是神经网络的训练又是图片，打标签也是用的图片
    
    读视频流，按每10fps/20fps保存一张图片
    
    全帧检测，还是抽帧检测？
    能几fps?
"""
import uuid

# 创建一个视频流对象
index = 0
file_name = r"D:\zhigu-ai-27-note\untitled2\opencv32\WIN_20240731_20_35_31_Pro.mp4"
# 是提前获取的
rtsp_url = r"http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8"
# 打开第0个摄像头
video = cv2.VideoCapture(rtsp_url)
i = 0
while True:
    ret, frame = video.read()  # 读1帧
    # persons1 =  检查是否有人的函数(frame) # 每个帧都检测
    # 30fps, 5fps
    if i % 6 == 0:
        # 抽帧检测
        #  persons2 =  检查是否有人的函数(frame)
        cv2.imwrite(f'./imgs/{str(uuid.uuid4())}.jpg', frame)
    if ret:  # 是否读成功
        cv2.imshow('video', frame)
        cv2.waitKey(1)
        i += 1
    if i == 30:
        i = 0
