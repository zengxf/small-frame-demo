import torch
import torch.nn as nn
import torch.optim as optim
import matplotlib.pyplot as plt
from torchvision import datasets, transforms
from torch.utils.data import DataLoader
from tqdm import tqdm
import os

"""
Einops允许你通过简单的字符串表达式来重新排列、重塑和减少数组的维度，而无需编写冗长且容易出错的代码。
"""
from einops import rearrange
from einops.layers.torch import Rearrange


def pair(t):
    return t if isinstance(t, tuple) else (t, t)


def posemb_sincos_2d(h, w, dim, temperature: int = 10000, dtype=torch.float32):
    """
    位置编码
    :param h:
    :param w:
    :param dim:
    :param temperature:
    :param dtype:
    :return:
    """
    y, x = torch.meshgrid(torch.arange(h), torch.arange(w), indexing="ij")  # 每个区域的位置编码
    assert (dim % 4) == 0, "feature dimension must be multiple of 4 for sincos emb"
    omega = torch.arange(dim // 4) / (dim // 4 - 1)  # 频率设置
    omega = 1.0 / (temperature ** omega)

    y = y.flatten()[:, None] * omega[None, :]
    x = x.flatten()[:, None] * omega[None, :]
    pe = torch.cat((x.sin(), x.cos(), y.sin(), y.cos()), dim=1)
    return pe.type(dtype)


class FeedForward(nn.Module):
    """
    FFN层
    """

    def __init__(self, dim, hidden_dim):
        super().__init__()
        self.net = nn.Sequential(
            nn.LayerNorm(dim),
            nn.Linear(dim, hidden_dim),
            nn.GELU(),
            nn.Linear(hidden_dim, dim),
        )

    def forward(self, x):
        return self.net(x)


class Attention(nn.Module):
    """
    多头注意力
    """

    def __init__(self, dim, heads=8, dim_head=64):
        super().__init__()
        inner_dim = dim_head * heads
        self.heads = heads
        self.scale = dim_head ** -0.5
        self.norm = nn.LayerNorm(dim)

        self.attend = nn.Softmax(dim=-1)

        self.to_qkv = nn.Linear(dim, inner_dim * 3, bias=False)
        self.to_out = nn.Linear(inner_dim, dim, bias=False)

    def forward(self, x):
        x = self.norm(x)

        qkv = self.to_qkv(x).chunk(3, dim=-1)
        q, k, v = map(lambda t: rearrange(t, 'b n (h d) -> b h n d', h=self.heads), qkv)

        dots = torch.matmul(q, k.transpose(-1, -2)) * self.scale

        attn = self.attend(dots)

        out = torch.matmul(attn, v)
        out = rearrange(out, 'b h n d -> b n (h d)')
        return self.to_out(out)


class Transformer(nn.Module):
    """
    只有encoder
    """

    def __init__(self, dim, depth, heads, dim_head, mlp_dim):
        super().__init__()
        self.norm = nn.LayerNorm(dim)
        self.layers = nn.ModuleList([])
        for _ in range(depth):
            self.layers.append(nn.ModuleList([
                Attention(dim, heads=heads, dim_head=dim_head),
                FeedForward(dim, mlp_dim)
            ]))

    def forward(self, x):
        for attn, ff in self.layers:
            x = attn(x) + x
            x = ff(x) + x
        return self.norm(x)


class SimpleViT(nn.Module):
    def __init__(self, *, image_size, patch_size, num_classes, dim, depth, heads, mlp_dim, channels=3, dim_head=64):
        super().__init__()
        image_height, image_width = pair(image_size)
        patch_height, patch_width = pair(patch_size)

        assert image_height % patch_height == 0 and image_width % patch_width == 0, 'Image dimensions must be divisible by the patch size.'

        patch_dim = channels * patch_height * patch_width
        # b 3 (256//32,32) (256//32,32) -> b (256//32,256//32) (32,32,3)

        self.patch = Rearrange("b c (h p1) (w p2) -> b (h w) (p1 p2 c)", p1=patch_height, p2=patch_width)
        self.to_patch_embedding = nn.Sequential(
            nn.LayerNorm(patch_dim),
            nn.Linear(patch_dim, dim),
            nn.LayerNorm(dim),
        )

        self.pos_embedding = posemb_sincos_2d(
            h=image_height // patch_height,
            w=image_width // patch_width,
            dim=dim,
        )

        self.transformer = Transformer(dim, depth, heads, dim_head, mlp_dim)

        self.pool = "mean"
        self.to_latent = nn.Identity()

        self.linear_head = nn.Linear(dim, num_classes)

    def forward(self, img):
        device = img.device
        # 分割成8x8的区域
        """
        输出为：1x(8*8)x(32,32,3)，即 1x64x(32,32,3)。
        输出的形状 1x64x3072 是因为每个补丁的大小是 32*32*3=3072，而总共有 64 个补丁
        
        64个补丁，每个补丁的特征映射为3072维向量
        
        相似之处：表示形式：
        1。补丁和词向量：在视觉模型中，图像被分成补丁，每个补丁都有一个特征表示；
        2。在 BERT 中，句子中的每个词也有一个向量表示。这两者都可以被看作是对输入的分块和特征化。
        处理方式：
        1。在视觉模型中，多个补丁的特征可以被用来捕捉整个图像的上下文信息；
        2。在 BERT 中，多个词的向量通过自注意力机制来理解句子的上下文。
        """
        x = self.patch(img)  # bx64x3072
        # 线性投射层
        x = self.to_patch_embedding(x).to("cpu") # b x 64 x 1024
        # 位置编码
        x += self.pos_embedding.to(device, dtype=x.dtype)
        # encoder
        x = self.transformer(x)  # 1x64x1024
        x = x.mean(dim=1)  # 取多通道中的第1个通道来做为分类预测
        # ffn
        x = self.to_latent(x)
        return self.linear_head(x)


if __name__ == "__main__":
    device = torch.device("cpu" if torch.cuda.is_available() else "cpu")
    # 保存特征图的列表
    feature_maps = []

    model = SimpleViT(
        image_size=256,
        patch_size=16,
        num_classes=1,
        dim=1024,  # Transformer 在编码过程中，输入的每个补丁会被转换为一个 1024 维的向量，以捕捉丰富的特征信息。
        depth=6,  # encoder重复的次数
        heads=16,  # 多头的数量
        mlp_dim=2048  # 全连接的神经元
    ).to(device)

    # 数据集路径
    data_dir = 'D:\\DLAI\\dataSet'

    # 数据预处理
    transform = transforms.Compose([
        transforms.Resize((256, 256)),
        transforms.ToTensor(),
    ])

    # 加载数据集
    train_dataset = datasets.ImageFolder(os.path.join(data_dir, 'train'), transform=transform)
    train_loader = DataLoader(train_dataset, batch_size=32, shuffle=True)

    val_dataset = datasets.ImageFolder(os.path.join(data_dir, 'val'), transform=transform)
    val_loader = DataLoader(val_dataset, batch_size=32, shuffle=False)
    criterion = nn.CrossEntropyLoss()
    optimizer = optim.Adam(model.parameters(), lr=0.001)

    # 训练和验证过程
    num_epochs = 1
    train_losses, train_accs, val_losses, val_accs = [], [], [], []

    for epoch in range(num_epochs):
        model.train()
        train_loss, correct = 0, 0
        total = 0

        for images, labels in tqdm(train_loader):
            images, labels = images.to(device), labels.to(device)

            optimizer.zero_grad()
            outputs = model(images)

            loss = criterion(outputs, labels)
            loss.backward()
            optimizer.step()

            train_loss += loss.item()
            _, predicted = torch.max(outputs.data, 1)
            total += labels.size(0)
            correct += (predicted == labels).sum().item()

        train_losses.append(train_loss / len(train_loader))
        train_accs.append(correct / total)

        # 验证过程
        model.eval()
        val_loss, val_correct = 0, 0
        val_total = 0

        with torch.no_grad():
            for images, labels in val_loader:
                images, labels = images.to(device), labels.to(device)
                outputs = model(images)
                loss = criterion(outputs, labels)
                val_loss += loss.item()
                _, val_predicted = torch.max(outputs.data, 1)
                val_total += labels.size(0)
                val_correct += (val_predicted == labels).sum().item()

        val_losses.append(val_loss / len(val_loader))
        val_accs.append(val_correct / val_total)

        print(f'Epoch [{epoch + 1}/{num_epochs}], '
              f'Train Loss: {train_losses[-1]:.4f}, Train Acc: {train_accs[-1]:.4f}, '
              f'Val Loss: {val_losses[-1]:.4f}, Val Acc: {val_accs[-1]:.4f}')
    # 保存模型权重
    torch.save(model.state_dict(), 'simple_vit_model.pth')
    # 绘图
    plt.figure(figsize=(12, 5))

    # 训练和验证损失
    plt.subplot(1, 2, 1)
    plt.plot(train_losses, label='Train Loss')
    plt.plot(val_losses, label='Validation Loss')
    plt.title('Loss')
    plt.xlabel('Epoch')
    plt.ylabel('Loss')
    plt.legend()

    # 训练和验证准确率
    plt.subplot(1, 2, 2)
    plt.plot(train_accs, label='Train Accuracy')
    plt.plot(val_accs, label='Validation Accuracy')
    plt.title('Accuracy')
    plt.xlabel('Epoch')
    plt.ylabel('Accuracy')
    plt.legend()

    plt.show()
