from PIL import Image
import os

image_dir = r'D:\Data\Test\AI-img\source_image'
new_image_dir = r'D:\Data\Test\AI-img\new_image_dir'
os.makedirs(new_image_dir, exist_ok=True)

images = os.listdir(image_dir)
for file in images:
    if file.endswith(('.png', '.bmp', '.jpeg', '.jpg')):
        with Image.open(os.path.join(image_dir, file)) as img:
            img.convert("RGB").save(os.path.join(new_image_dir, os.path.splitext(file)[0] + '.jpg'))
            # img.convert("L").save(os.path.join(new_image_dir, os.path.splitext(file)[0] + '.jpg'))
