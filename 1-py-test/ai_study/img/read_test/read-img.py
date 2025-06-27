from PIL import Image

image_path = r'222.jpg'
# img = Image.open(image_path)
# img.close()

with Image.open(image_path) as img:
    img_copy = img.copy()  # 浅拷贝
    img_copy.show()  # 显示图片
    print(img.size)
    print(img.mode)
# 推荐使用 with，优点代码简洁，自动管理资源。如果直接打开还需要 close 关闭
