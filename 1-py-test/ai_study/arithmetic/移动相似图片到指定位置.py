import os
import hashlib
import shutil
from PIL import Image
import imagehash
import cv2
import numpy as np
from skimage.metrics import structural_similarity as ssim
import cv2

# 配置参数
SIMILARITY_THRESHOLD = 5     # 汉明距离阈值（pHash）
SSIM_THRESHOLD = 0.8        # SSIM相似度阈值
EXACT_DUP_DIR = "exact_duplicates"   # 完全重复文件目录
SIMILAR_DUP_DIR = "similar_duplicates"  # 视觉相似文件目录

def get_file_hash(file_path):
    """计算文件的MD5哈希值"""
    hasher = hashlib.md5()
    with open(file_path, "rb") as f:
        for chunk in iter(lambda: f.read(4096), b""):
            hasher.update(chunk)
    return hasher.hexdigest()

def get_phash(file_path):
    """生成感知哈希（pHash）"""
    try:
        img = Image.open(file_path)
        return imagehash.phash(img)
    except Exception as e:
        print(f"Error processing {file_path}: {e}")
        return None


def ssim_check(img1_path, img2_path):
    """计算结构相似性（SSIM）"""
    img1 = cv2.imread(img1_path)
    img2 = cv2.imread(img2_path)
    if img1 is None or img2 is None:
        return 0

    # 确保两张图片大小一致
    img2 = cv2.resize(img2, (img1.shape[1], img1.shape[0]))

    # 转换为灰度图
    gray1 = cv2.cvtColor(img1, cv2.COLOR_BGR2GRAY)
    gray2 = cv2.cvtColor(img2, cv2.COLOR_BGR2GRAY)

    # 计算 SSIM
    ssim_value, _ = ssim(gray1, gray2, full=True)
    print(ssim_value)
    return ssim_value

def generate_unique_path(dest_dir, filename):
    """生成唯一文件名，避免覆盖"""
    base, ext = os.path.splitext(filename)
    counter = 1
    while True:
        new_path = os.path.join(dest_dir, filename)
        if not os.path.exists(new_path):
            return new_path
        new_filename = f"{base}_{counter}{ext}"
        new_path = os.path.join(dest_dir, new_filename)
        counter += 1

def move_files(files, dest_dir, exclude_files=None):
    """移动文件到目标目录，排除已处理的文件"""
    exclude_files = exclude_files or set()
    moved = []
    for file in files:
        if file in exclude_files or not os.path.exists(file):
            continue
        filename = os.path.basename(file)
        dest_path = generate_unique_path(dest_dir, filename)
        try:
            shutil.move(file, dest_path)
            moved.append(file)
            print(f"Moved: {file} -> {dest_path}")
        except Exception as e:
            print(f"Failed to move {file}: {str(e)}")
    return moved

def find_and_move_duplicates(target_dir):
    exact_dest = os.path.join(target_dir, EXACT_DUP_DIR)
    similar_dest = os.path.join(target_dir, SIMILAR_DUP_DIR)
    os.makedirs(exact_dest, exist_ok=True)
    os.makedirs(similar_dest, exist_ok=True)

    # 查找重复项
    exact_dups, similar_dups = find_duplicates(target_dir)

    # 移动完全重复文件（每组保留第一个）
    exact_files_to_move = []
    for group in exact_dups:
        exact_files_to_move.extend(group[1:])  # 保留第一个，移动其余

    moved_exact = move_files(exact_files_to_move, exact_dest)

    # 移动视觉相似文件（排除已处理的完全重复文件）
    similar_files_to_move = []
    for pair in similar_dups:
        similar_files_to_move.extend([pair[0], pair[1]])

    moved_similar = move_files(similar_files_to_move, similar_dest, set(moved_exact))

    return moved_exact, moved_similar

def find_duplicates(directory):
    # 第一步：查找完全相同的文件（MD5哈希）
    md5_dict = {}
    for root, _, files in os.walk(directory):
        for file in files:
            if file.lower().endswith(('.png', '.jpg', '.jpeg', '.bmp', '.gif')):
                path = os.path.join(root, file)
                file_hash = get_file_hash(path)
                if file_hash in md5_dict:
                    md5_dict[file_hash].append(path)
                else:
                    md5_dict[file_hash] = [path]
    exact_duplicates = [files for files in md5_dict.values() if len(files) > 1]

    # 第二步：查找视觉相似文件（pHash + SSIM）
    phash_dict = {}
    for root, _, files in os.walk(directory):
        for file in files:
            if file.lower().endswith(('.png', '.jpg', '.jpeg', '.bmp', '.gif')):
                path = os.path.join(root, file)
                phash = get_phash(path)
                if phash is not None:
                    phash_dict[path] = phash

    similar_duplicates = []
    checked = set()
    for path1, hash1 in phash_dict.items():
        for path2, hash2 in phash_dict.items():
            if path1 != path2 and path2 not in checked:
                hamming_distance = hash1 - hash2
                if hamming_distance <= SIMILARITY_THRESHOLD:
                    # 二次验证：SSIM
                    ssim = ssim_check(path1, path2)
                    if ssim >= SSIM_THRESHOLD:
                        similar_duplicates.append((path1, path2, hamming_distance, ssim))
        checked.add(path1)

    return exact_duplicates, similar_duplicates


if __name__ == "__main__":
    root_dir = r"F:\2025\py_do2025\DataClean\clothes_color_2"
    for p in os.listdir(root_dir):
        if 'bing' in p:
            # if p not in ["bing_images_aquamarine"]:
            #     continue

            target_dir = f"{root_dir}\\{p}"
            print(target_dir)
            moved_exact, moved_similar = find_and_move_duplicates(target_dir)

            print(f"\n移动完成！"
                  f"\n- 完全重复文件：{len(moved_exact)} 个"
                  f"\n- 视觉相似文件：{len(moved_similar)} 个"
                  f"\n查看目录："
                  f"\n- {os.path.join(target_dir, EXACT_DUP_DIR)}"
                  f"\n- {os.path.join(target_dir, SIMILAR_DUP_DIR)}")