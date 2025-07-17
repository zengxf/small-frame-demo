import cv2


def show(img, title=''):
    cv2.imshow(title, img)
    cv2.waitKey(0)


# 等比例图像缩放并填充
def letterbox(image, size=(640, 640), boxes=None):
    ih, iw = image.shape[:2]
    w, h = size

    if ih == h and iw == w:
        return image, boxes, 1, 0, 0, 0, 0  # image, boxes, ratio, padT, padB, padL, padR

    ratio = min(w / iw, h / ih)

    new_w = int(iw * ratio)
    new_h = int(ih * ratio)

    dw = (w - new_w) // 2
    dh = (h - new_h) // 2

    scale_img = cv2.resize(image, [new_w, new_h])

    padded_image = cv2.copyMakeBorder(scale_img,
                                      dh, h - dh - new_h, dw, w - dw - new_w,
                                      cv2.BORDER_CONSTANT,
                                      value=[114, 114, 114])
    return padded_image


if __name__ == "__main__":
    img = cv2.imread(r"D:/Data/Test/img/lenaNoise2.png")
    img2 = letterbox(img)
    show(img2, 'resize')
