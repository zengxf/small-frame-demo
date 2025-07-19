''' 文件清洗：图片文件和标签文件
1. 没有标签文件的图片
2. 文件和图片名称，重命名标识符（字母、数字、下划线）
3. 有图片和对应标签，划分数据集 '''

# 先备份数据 shutil 模块
''' 1）把 ImageSets 标签，拷贝并重命名 labels，JPEGImages 图片 拷贝并重命名 images'''
# 读 labels 文件，文件不能为空，保存文件名，对应 images 目录下，拷贝图片


import shutil
import os
import uuid


def process_file(label_dir, image_dir, new_labels, new_images):
    # 获取标签文件，确保文件都是 txt
    label_files = os.listdir(label_dir)
    for file in label_files:
        if not file.endswith('.txt'):
            os.remove(os.path.join(label_dir, file))

        file_path = os.path.join(label_dir, file)
        image_path = os.path.join(image_dir, os.path.splitext(file)[0] + '.jpg')

        if os.path.getsize(file_path) == 0:
            shutil.move(file_path, new_labels)
            shutil.move(image_path, new_images)
        else:
            # file = '01_missing_hole_01_jpg.rf.8bc03a94396d5e7d3e3f1961b3aaf308.txt'
            # new_file = '01_missing_hole_01_{uid}.txt'
            uid = str(uuid.uuid4()).replace('-', '')
            new_label_name = os.path.join(old_labels, file.split('_jpg')[0] + uid + '.txt')
            new_image_name = os.path.join(old_images, file.split('_jpg')[0] + uid + '.jpg')

            os.rename(file_path, new_label_name)
            os.rename(image_path, new_image_name)

    return


if __name__ == '__main__':
    # 复制数据源目录
    data_dir = r'D:\Data\Test\AI-img\pcb_dataset'
    shutil.copytree(os.path.join(data_dir, 'ImageSets'), os.path.join(data_dir, 'labels'), dirs_exist_ok=True)
    shutil.copytree(os.path.join(data_dir, 'JPEGImages'), os.path.join(data_dir, 'images'), dirs_exist_ok=True)

    # 创建新目录，移除没有标签的图片，标签文件为空的文件，并重名名
    new_images = os.path.join(data_dir, 'new_images')
    new_labels = os.path.join(data_dir, 'new_labels')
    os.makedirs(new_images, exist_ok=True)
    os.makedirs(new_labels, exist_ok=True)

    # 旧的目录
    old_images = os.path.join(data_dir, 'images')
    old_labels = os.path.join(data_dir, 'labels')

    process_file(old_labels, old_images, new_labels, new_images)
