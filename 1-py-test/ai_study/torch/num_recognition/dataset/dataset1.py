import os
from torch.utils.data import Dataset, DataLoader
from torchvision.transforms import Grayscale, Resize, ToTensor
from PIL import Image


class XDataset(Dataset):
    def __init__(self, root_dir):
        self.root_dir = os.path.expanduser(root_dir)
        self.transforms = [
            Grayscale(),
            Resize((32, 32)),
            ToTensor()
        ]
        self.image_paths = []
        self.labels = []
        for file_name in os.listdir(self.root_dir):
            if file_name.endswith('.png'):
                label, _ = file_name.split('_')
                image_path = os.path.join(self.root_dir, file_name)
                self.image_paths.append(image_path)
                self.labels.append(int(label))

    def __len__(self):
        return len(self.image_paths)

    def __getitem__(self, index):
        image = Image.open(self.image_paths[index])
        label = self.labels[index]
        for transform in self.transforms:
            image = transform(image)
        return image, label


if __name__ == '__main__':
    root_dir = r'D:/Data/Test/img/numeral_img/numeral_img_2/training_img'
    xdataset = XDataset(root_dir=root_dir)
    xdataLoader = DataLoader(xdataset, batch_size=20, shuffle=True)

    for image, label in xdataLoader:
        print('image: ', image.size(), 'label: ', label.size())

# image:  torch.Size([20, 1, 32, 32]) label:  torch.Size([20])
# image:  torch.Size([20, 1, 32, 32]) label:  torch.Size([20])
# image:  torch.Size([14, 1, 32, 32]) label:  torch.Size([14])