import json
import numpy as np
import cv2
from pathlib import Path


def labelme_points_to_mask(json_path, output_path=None):
    """
    将LabelMe的JSON标注文件中的点标注转换为二值Mask图像

    参数:
        json_path: LabelMe JSON文件路径
        output_path: 输出的Mask图像路径(可选)

    返回:
        mask: 生成的二值Mask图像(目标区域255,背景0)
    """
    # 读取JSON文件
    with open(json_path, 'r', encoding='utf-8') as f:
        data = json.load(f)

    # 获取图像尺寸
    img_height = data['imageHeight']
    img_width = data['imageWidth']

    # 创建空白图像
    mask = np.zeros((img_height, img_width), dtype=np.uint8)

    # 遍历所有标注
    for shape in data['shapes']:
        if shape['shape_type'] == 'polygon':
            # 获取多边形点坐标
            points = np.array(shape['points'], dtype=np.int32)

            # 在mask上绘制填充多边形
            cv2.fillPoly(mask, [points], color=255)

    # 如果需要保存
    if output_path:
        cv2.imwrite(output_path, mask)

    return mask


# 示例用法
if __name__ == "__main__":
    json_file = r"D:\26--ppt\face_data\face_data\labels\ddddd.json"  # 替换为你的LabelMe JSON文件
    output_mask = "mask.png"  # 输出的mask文件名

    mask = labelme_points_to_mask(json_file, output_mask)
    print(f"Mask图像已生成，尺寸: {mask.shape}")