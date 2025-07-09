import cv2
import os
import numpy as np


# Sigmoid()函数
def sigmoid(z):
    return 1 / (1 + np.exp(-z))


# 逻辑回归的损失函数
def cost_loss(X, Y, theta):
    # X为样本,Y为真实值
    # 根据权重theta得到预测值
    pre_Y = sigmoid(np.matmul(X, theta))
    # 损失计算参考公式(2-37)
    result = -np.sum(Y * np.log(pre_Y) +
                     (1 - Y) * np.log(1 - pre_Y)) / len(Y)
    return result


if __name__ == "__main__":
    path = r"D:/Data/Test/img/numeral_img/training_img"
    images_dir = os.listdir(path)
    # 0,0_png
    image_name = {str(x): [] for x in range(10)}
    for img in images_dir:
        file_num = img.split('_')[0]
        img_numpy = cv2.imread(f'{path}/{img}', 0)  # RGB, 0灰度值。 [
        if img_numpy is not None:
            img_numpy = img_numpy / 255.0  # 归一化。如果不/255.0，会出现梯度爆炸。nan
            img_numpy = np.reshape(img_numpy, [-1])  # [-1,2]
            image_name[file_num].append(img_numpy)  # 将img_numpy的值添加到{0:[图片1,图片2])
    # 1933 x 1024
    train_data = np.concatenate([np.array(image_name[img]) for img in image_name], axis=0)


    def grad_logic_by_num(num='2', alpha=1e-4, epoch=1000):
        end_num = [len(image_name[str(num)]) for num in range(10)]

        X = train_data  # 训练集
        Y = np.zeros(X.shape[0])
        # 后面的 不同的类别，矩阵中的开始和结束的索引不同
        endIndex = 0
        startIndex = 0
        # num='1'
        for _i in range(int(num) + 1):
            endIndex += end_num[_i]
            if _i < int(num):
                startIndex += end_num[_i]
        Y[startIndex:endIndex] = 1
        # 这是一个错误的方法
        # 1.只喂0的数据，其它不是0的图片没有喂进去--> 0/1代表是不是0
        #   只喂1的数据，其它不是1的图片没有喂进去--> 0/1代表是不是0
        #   只喂2的数据，其它不是2的图片没有喂进去--> 0/1代表是不是0
        # X = np.array(image_name[num])
        # Y = np.ones(X.shape[0])

        # if num == '0':
        #     Y[:end_num[0]] = 1
        # elif num == '1':
        #     Y[end_num[0]:end_num[0] + end_num[1]] = 1
        # elif num == '2':
        #     Y[end_num[0] + end_num[1]:end_num[0] + end_num[1]+end_num[2]] = 1
        # elif num == '3':
        #     Y[end_num[0] + end_num[1]+end_num[2]:end_num[0] + end_num[1]+end_num[2]+end_num[3]] = 1
        # elif num == '4':
        #     Y[end_num[0] + end_num[1]+end_num[2]+end_num[3]:end_num[0] + end_num[1]+end_num[2]+end_num[3]+end_num[4]] = 1
        # elif num == '5':
        #     Y[end_num[0] + end_num[1] + end_num[2] + end_num[3] +end_num[4]:
        #       end_num[0] + end_num[1] + end_num[2] + end_num[3] +end_num[4]+end_num[5]] = 1
        # elif num == '6':
        #     Y[end_num[0] + end_num[1] + end_num[2] + end_num[3] +end_num[4]+end_num[5]:
        #       end_num[0] + end_num[1] + end_num[2] + end_num[3] +end_num[4]+end_num[5]+end_num[6]] = 1
        # elif num == '7':
        #     Y[end_num[0] + end_num[1] + end_num[2] + end_num[3] +end_num[4]+end_num[5]+end_num[6]:
        #       end_num[0] + end_num[1] + end_num[2] + end_num[3] +end_num[4]+end_num[5]+end_num[6]+end_num[7]] = 1
        # elif num == '8':
        #     Y[end_num[0] + end_num[1] + end_num[2] + end_num[3] +end_num[4]+end_num[5]+end_num[6]+end_num[7]:
        #       end_num[0] + end_num[1] + end_num[2] + end_num[3] +end_num[4]+end_num[5]+end_num[6]+end_num[7]+end_num[8]] = 1
        # elif num == '9':
        #     Y[end_num[0] + end_num[1] + end_num[2] + end_num[3] +end_num[4]+end_num[5]+end_num[6]+end_num[7]+end_num[8]:
        #       end_num[0] + end_num[1] + end_num[2] + end_num[3] +end_num[4]+end_num[5]+end_num[6]+end_num[7]+end_num[8]+end_num[9]] = 1

        # 前面的189为1
        Y = np.reshape(Y, [-1, 1])
        # 初始权重
        theta = np.zeros([X.shape[1], 1])
        for i in range(epoch):
            gradient = np.matmul(X.T, (sigmoid(np.matmul(X, theta)) - Y))
            # 梯度下降
            theta = theta - alpha * gradient
            # 求损失
            loss = cost_loss(X, Y, theta)
            if i % 20 == 0 or i == 99:
                print('逻辑回归手写数字识别_{},第{}次,损失={}'.format(num, i + 1, loss))
        # 将最后1次的权重写到numpy文件中
        os.makedirs('D:/Data/Test/img/numeral_img/weights', exist_ok=True)
        np.save(f'D:/Data/Test/img/numeral_img/weights/逻辑回归手写数字识别_{num}.npy', theta)


    # 构建逻辑回归0的识别器 训练10个二分类  0[0/1], 1[0/1], 2[0/1]
    [grad_logic_by_num(num=str(i)) for i in range(10)]
    ##################################################
    # 训练逻辑和预测逻辑，应该是分开的。在这里只是为了演示一下

    # 预测
    pred_img = cv2.imread(f'D:/Data/Test/img/numeral_img/training_img/5_124.png', 0) / 255.0
    # 训练的时候X(1,1024)与训练时要保持一致
    X = np.reshape(pred_img, [1, -1])
    # 导入的theta是[1024,1)与训练时要保持一致
    theta_list = [np.load(f'D:/Data/Test/img/numeral_img/weights/逻辑回归手写数字识别_{str(i)}.npy') for i in range(10)]
    # 得到9个类别的概率
    pred = np.array([sigmoid(np.matmul(X, theta)) for theta in theta_list])
    # 降成1维
    pred = np.reshape(pred, [-1])
    index = np.argmax(pred)
    print(f'预测概率={pred[index]}，目标为={index}')
