''' 划分数据集：8、2
1. 训练集80%、验证集20%            给别人做项目（用户真实数据测试）
2. 训练集70%、验证集15%、测试集15% '''

import os
import shutil
import random


def split_data(label_dir, image_dir, dataset):
    # 获取文件额数量
    label_files = os.listdir(label_dir)
    # 随机打乱的数据
    random.shuffle(label_files)

    # 划分点：0.7, 0.15, 0.15
    point1 = int(len(label_files) * 0.70)
    point2 = int(len(label_files) * 0.15)

    # 取数据
    trina1 = label_files[: point1]
    val2 = label_files[point1 : point1 + point2]
    test3 = label_files[point1 + point2 :]

    # 拼接文件名
    for file in trina1:
        file_name, ext = os.path.splitext(file)
        label_file_name = os.path.join(label_dir, file)
        shutil.copy(label_file_name, os.path.join(dataset, r'trina\labels'))

        image_file_name = os.path.join(image_dir, file_name+'.jpg')
        shutil.copy(image_file_name, os.path.join(dataset, r'trina\images'))

    pass


if __name__ == '__main__':
    # 源数据集
    image_dir = r'C:\Study\datapro\pcb_dataset\images'
    label_dir = r'C:\Study\datapro\pcb_dataset\labels'

    # 创建三个目录：trina、val、test
    dataset = r'C:\Study\datapro\pcb_dataset\dataset'
    os.makedirs(dataset, exist_ok=True)
    for dir in ['trina', 'val', 'test']:
        os.makedirs(os.path.join(dataset, dir), exist_ok=True)

        # trina、val、test 目录分别创建 images、labels
        for sub_dir in ['images', 'labels']:
            os.makedirs(os.path.join(dataset, dir, sub_dir), exist_ok=True)

    split_data(label_dir, image_dir, dataset)