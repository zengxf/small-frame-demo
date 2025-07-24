"""
1. face检测人脸这个模型

2. face 关键点检测这个模型

3. 手势自动拍照检测

研发时间：3-5个月左右
人员：模型训练、调优、数据清洗、整个算法的逻辑合并和推演  算法工程师  1+实习生(0.5)

     1. 工作安排
        第一个月的主要工作
            1.0 出demo模型，不要求精度
            2.0 整个算法逻辑的合并 ( 最重要的工作）

        第二个月的主要工作
            清洗数据，提升精度
            同时配合其它工程师

        第三个月的主要工作
            解决测试场景下的误检和漏检问题以及进一步提升精度

     部署工程师：app端android, ios端, 2个人  + 2个人(应用）
              蓝牙通信 1个人

     服务端开发：业务系统开发2个人

     测试：1.5个人

     产品：1个

     整个人员：12个人

     ####################################

     自拍杆里面： 嵌入2个人。结构硬件1个人
                算法1.5个人。测试1个人。产品1个人




python的推理代码演示：
1. 将3个模型导出转onnx模型, float16
    input[640x480]->model->decode[输出的bouding box是多少？]

2. 初始化模型
    # 这里表示全局
    始终加载在内存中的
    import threading

    face_detect_model = init_loadonnx()   3.7MB
    face_keypoint_model = init_loadonnx() 3.7MB
    hand_detect_model = init_loadonn()    3.7MB

    # 预录入人脸
    # 人脸模板处理存储
    # 预置的人脸关键点
    def temple_face_point(model,cut_face):
        [face_temple] = face_detect_forward(face_detect_model,temple_face)
        temple_point = face_keypoint_forward(face_keypoint_model,face_temple)
        return temple_point
    # 预录入point
    temple_point = temple_face_point(face_detect_model,face_keypoint_model)

    ＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃
    def face_detect_forward(model,全景图):
        pre_image() # hwc ->bchw
        forward()

        if rknn:解码
        ths_nms() # 阈值过滤
        cut_face() # 将所有的人脸切下来
        return [face1,face2]

    def face_keypoint_forward(model,cut_face):
        letterbox() # 就不进行padding 关键点检测的输入尺寸是多少,112x112
        cut_face_pre_image() #hwc->bchw
        forward()
        if rknn:解码
        ths_nms()
        return 108point

    def hand_forward(model,全景图):
        pre_image() # hwc ->bchw
        forward()
        if rknn:解码
        ths_nms() # 阈值过滤
        return 手势的类别

    def similar(temple_point,face_point):
        return 余弦相似度 + 图相似度

    def xiang_ji_ju_jiao(frame):
        # 人脸的推理
        face_list = face_detect_forward(face_detect_model,frame)
        for face in face_list:
            face_point = face_keypoint_forward(face_keypoint_model,face)
            # 得到每个人脸的相似度得分
            similar_socre = similar(temple_point,face_point)
            if similar_socre>0.6:
                # [1, cx,cy, w, h]
                send(一个消息给相机，相机这边就聚集到我的人脸这个位置）

    def hand_paizhao(frame):
        手势类别 = hand_forward(frame)

    # 相机来调的过程
    def main_pre(全景图):
        video = 打开相机()
        while true:
            ret, frame = video.get_frame()
            if(是否开启主人聚焦，是否开启主人跟焦)
                # 第一个线程，对全景图做是否为当前人脸的推理
                threading1.func(xiang_ji_ju_jiao, frame)

            if(是否手势自动拍照)
                手势类别 = threading2.func(hand_paizhao, frame)
                if 手势类别 和 [11种类别中] 取交集:
                    # send(2)
                    send(给相机发送一个消息，执行自动拍照功能。相机倒计时N秒(相机这边可以设置的))

            threading1.json()
            threading2.json()

最终部署的位置：
1. 在自拍杆中板端RKNN进行操作
    onnx->rknn， PTQ int8的校准过程

    input[640x480]->model->没有decode

    按上面的逻辑来进行组合


2. APP通过蓝牙与自拍杆进行通信
    android: https://blog.csdn.net/xs1997/article/details/131747372

    onnx->ncnn[类似]中的工具转成(onnx2ncnn , .param 参数， .bin 模型结构)
          也是不带decode

    写代码C++ -> 完成编译.so文件 -->android开发用

    ios:

工业大型设备焊孔检测
1. 全局机相，获取按区域获取所有的圆孔 3k C模型, yolov8 [先目标检测->opencv 圆检测]
2. 将每个孔进行CAD映射，规划2D相机的路径
3. 裸金属的缺陷检测【检出的所有缺陷+误检缺陷 A模型】 yolov5  检出>95%, 误检<40% (根据每个批次，已知数量缺陷)

    分割的误检非常高。分割会倾向像素的一致性认为是同一个物体。估不准他的边

4. 对所有的再次PT复检 B模型 【后期要去掉整个步骤4】【原来：本来想的是对A的内容进行PT复检】 yolov5 检出100%，误检<5%

    为什么A，B同时保留，A能采集到缺陷数据现在数量不够。A光线，干扰物特别多，误检比较高。

验收环节：人工复检（人工把缺陷找出来，然后AI算法对比）

1 -> 2 ->3 -> 4

"""








