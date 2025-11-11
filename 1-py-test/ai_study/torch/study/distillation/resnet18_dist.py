"""
知识蒸馏 Demo

ref: https://chat.deepseek.com/a/chat/s/c12f13c8-12ef-4a4f-adc2-1fa4e318810b

下载出错时，Clash 用全局 (SG 节点)
"""

import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
from torch.utils.data import DataLoader
import torchvision
import torchvision.transforms as transforms
import matplotlib.pyplot as plt


plt.rcParams['font.sans-serif'] = ['SimHei']	# 解决中文显示问题
plt.rcParams['axes.unicode_minus'] = False

# 设置设备
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
print(f"使用设备: {device}")

# 超参数
batch_size = 128
learning_rate = 0.01
temperature = 4  # 蒸馏温度
alpha = 0.7  # 蒸馏损失权重
epochs = 5

# 加载CIFAR-10数据集
transform = transforms.Compose([
    transforms.ToTensor(),
    transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5))
])

train_dataset = torchvision.datasets.CIFAR10(root='./data', train=True, download=True, transform=transform)
test_dataset = torchvision.datasets.CIFAR10(root='./data', train=False, download=True, transform=transform)

train_loader = DataLoader(train_dataset, batch_size=batch_size, shuffle=True)
test_loader = DataLoader(test_dataset, batch_size=batch_size, shuffle=False)


# 定义教师模型（较大的模型）
class TeacherModel(nn.Module):
    def __init__(self, num_classes=10):
        super(TeacherModel, self).__init__()
        self.features = nn.Sequential(
            nn.Conv2d(3, 64, kernel_size=3, padding=1),
            nn.ReLU(inplace=True),
            nn.Conv2d(64, 64, kernel_size=3, padding=1),
            nn.ReLU(inplace=True),
            nn.MaxPool2d(kernel_size=2, stride=2),

            nn.Conv2d(64, 128, kernel_size=3, padding=1),
            nn.ReLU(inplace=True),
            nn.Conv2d(128, 128, kernel_size=3, padding=1),
            nn.ReLU(inplace=True),
            nn.MaxPool2d(kernel_size=2, stride=2),

            nn.Conv2d(128, 256, kernel_size=3, padding=1),
            nn.ReLU(inplace=True),
            nn.Conv2d(256, 256, kernel_size=3, padding=1),
            nn.ReLU(inplace=True),
            nn.MaxPool2d(kernel_size=2, stride=2),
        )
        self.classifier = nn.Sequential(
            nn.Dropout(0.5),
            nn.Linear(256 * 4 * 4, 512),
            nn.ReLU(inplace=True),
            nn.Dropout(0.5),
            nn.Linear(512, num_classes)
        )

    def forward(self, x):
        x = self.features(x)
        x = x.view(x.size(0), -1)
        x = self.classifier(x)
        return x


# 定义学生模型（较小的模型）
class StudentModel(nn.Module):
    def __init__(self, num_classes=10):
        super(StudentModel, self).__init__()
        self.features = nn.Sequential(
            nn.Conv2d(3, 32, kernel_size=3, padding=1),
            nn.ReLU(inplace=True),
            nn.MaxPool2d(kernel_size=2, stride=2),

            nn.Conv2d(32, 64, kernel_size=3, padding=1),
            nn.ReLU(inplace=True),
            nn.MaxPool2d(kernel_size=2, stride=2),
        )
        self.classifier = nn.Sequential(
            nn.Linear(64 * 8 * 8, 128),
            nn.ReLU(inplace=True),
            nn.Linear(128, num_classes)
        )

    def forward(self, x):
        x = self.features(x)
        x = x.view(x.size(0), -1)
        x = self.classifier(x)
        return x


# 初始化模型
teacher_model = TeacherModel().to(device)
student_model = StudentModel().to(device)

# 计算参数量
teacher_params = sum(p.numel() for p in teacher_model.parameters())
student_params = sum(p.numel() for p in student_model.parameters())
print(f"教师模型参数量: {teacher_params:,}")
print(f"学生模型参数量: {student_params:,}")
print(f"模型压缩率: {teacher_params / student_params:.2f}x")

# 首先训练教师模型
print("\n训练教师模型...")
criterion = nn.CrossEntropyLoss()
optimizer = optim.Adam(teacher_model.parameters(), lr=learning_rate)

teacher_train_loss = []
teacher_train_acc = []

for epoch in range(epochs):
    teacher_model.train()
    running_loss = 0.0
    correct = 0
    total = 0

    for i, (inputs, labels) in enumerate(train_loader):
        inputs, labels = inputs.to(device), labels.to(device)

        optimizer.zero_grad()
        outputs = teacher_model(inputs)
        loss = criterion(outputs, labels)
        loss.backward()
        optimizer.step()

        running_loss += loss.item()
        _, predicted = outputs.max(1)
        total += labels.size(0)
        correct += predicted.eq(labels).sum().item()

    epoch_loss = running_loss / len(train_loader)
    epoch_acc = 100. * correct / total
    teacher_train_loss.append(epoch_loss)
    teacher_train_acc.append(epoch_acc)

    print(f"教师模型 Epoch [{epoch + 1}/{epochs}], 损失: {epoch_loss:.4f}, 准确率: {epoch_acc:.2f}%")

# 测试教师模型
teacher_model.eval()
correct = 0
total = 0
with torch.no_grad():
    for inputs, labels in test_loader:
        inputs, labels = inputs.to(device), labels.to(device)
        outputs = teacher_model(inputs)
        _, predicted = outputs.max(1)
        total += labels.size(0)
        correct += predicted.eq(labels).sum().item()

teacher_test_acc = 100. * correct / total
print(f"教师模型测试准确率: {teacher_test_acc:.2f}%")

# 知识蒸馏训练学生模型
print("\n通过知识蒸馏训练学生模型...")
optimizer_student = optim.Adam(student_model.parameters(), lr=learning_rate)

distill_train_loss = []
distill_train_acc = []

for epoch in range(epochs):
    student_model.train()
    teacher_model.eval()  # 教师模型固定

    running_loss = 0.0
    correct = 0
    total = 0

    for i, (inputs, labels) in enumerate(train_loader):
        inputs, labels = inputs.to(device), labels.to(device)

        optimizer_student.zero_grad()

        # 获取教师模型的软标签（使用温度参数）
        with torch.no_grad():
            teacher_logits = teacher_model(inputs)
            teacher_probs = F.softmax(teacher_logits / temperature, dim=1)

        # 获取学生模型的输出
        student_logits = student_model(inputs)
        student_probs = F.log_softmax(student_logits / temperature, dim=1)

        # 计算蒸馏损失（KL散度）
        distill_loss = F.kl_div(student_probs, teacher_probs, reduction='batchmean') * (temperature ** 2)

        # 计算学生损失（标准交叉熵）
        student_loss = F.cross_entropy(student_logits, labels)

        # 总损失 = α * 蒸馏损失 + (1-α) * 学生损失
        total_loss = alpha * distill_loss + (1 - alpha) * student_loss

        total_loss.backward()
        optimizer_student.step()

        running_loss += total_loss.item()
        _, predicted = student_logits.max(1)
        total += labels.size(0)
        correct += predicted.eq(labels).sum().item()

    epoch_loss = running_loss / len(train_loader)
    epoch_acc = 100. * correct / total
    distill_train_loss.append(epoch_loss)
    distill_train_acc.append(epoch_acc)

    print(f"蒸馏训练 Epoch [{epoch + 1}/{epochs}], 损失: {epoch_loss:.4f}, 准确率: {epoch_acc:.2f}%")

# 测试学生模型
student_model.eval()
correct = 0
total = 0
with torch.no_grad():
    for inputs, labels in test_loader:
        inputs, labels = inputs.to(device), labels.to(device)
        outputs = student_model(inputs)
        _, predicted = outputs.max(1)
        total += labels.size(0)
        correct += predicted.eq(labels).sum().item()

student_test_acc = 100. * correct / total
print(f"学生模型测试准确率: {student_test_acc:.2f}%")

# 对比正常训练的学生模型（不使用蒸馏）
print("\n正常训练学生模型（不使用蒸馏）...")
normal_student_model = StudentModel().to(device)
optimizer_normal = optim.Adam(normal_student_model.parameters(), lr=learning_rate)

normal_train_loss = []
normal_train_acc = []

for epoch in range(epochs):
    normal_student_model.train()
    running_loss = 0.0
    correct = 0
    total = 0

    for i, (inputs, labels) in enumerate(train_loader):
        inputs, labels = inputs.to(device), labels.to(device)

        optimizer_normal.zero_grad()
        outputs = normal_student_model(inputs)
        loss = criterion(outputs, labels)
        loss.backward()
        optimizer_normal.step()

        running_loss += loss.item()
        _, predicted = outputs.max(1)
        total += labels.size(0)
        correct += predicted.eq(labels).sum().item()

    epoch_loss = running_loss / len(train_loader)
    epoch_acc = 100. * correct / total
    normal_train_loss.append(epoch_loss)
    normal_train_acc.append(epoch_acc)

    print(f"正常训练 Epoch [{epoch + 1}/{epochs}], 损失: {epoch_loss:.4f}, 准确率: {epoch_acc:.2f}%")

# 测试正常训练的学生模型
normal_student_model.eval()
correct = 0
total = 0
with torch.no_grad():
    for inputs, labels in test_loader:
        inputs, labels = inputs.to(device), labels.to(device)
        outputs = normal_student_model(inputs)
        _, predicted = outputs.max(1)
        total += labels.size(0)
        correct += predicted.eq(labels).sum().item()

normal_student_test_acc = 100. * correct / total
print(f"正常训练学生模型测试准确率: {normal_student_test_acc:.2f}%")

# 绘制训练曲线
plt.figure(figsize=(12, 4))

plt.subplot(1, 2, 1)
plt.plot(teacher_train_loss, label='教师模型')
plt.plot(distill_train_loss, label='蒸馏训练学生模型')
plt.plot(normal_train_loss, label='正常训练学生模型')
plt.xlabel('Epoch')
plt.ylabel('Loss')
plt.title('训练损失')
plt.legend()

plt.subplot(1, 2, 2)
plt.plot(teacher_train_acc, label='教师模型')
plt.plot(distill_train_acc, label='蒸馏训练学生模型')
plt.plot(normal_train_acc, label='正常训练学生模型')
plt.xlabel('Epoch')
plt.ylabel('Accuracy (%)')
plt.title('训练准确率')
plt.legend()

plt.tight_layout()
plt.show()

# 打印最终结果对比
print("\n=== 最终结果对比 ===")
print(f"教师模型测试准确率: {teacher_test_acc:.2f}%")
print(f"学生模型测试准确率 (知识蒸馏): {student_test_acc:.2f}%")
print(f"学生模型测试准确率 (正常训练): {normal_student_test_acc:.2f}%")
print(f"知识蒸馏带来的性能提升: {student_test_acc - normal_student_test_acc:.2f}%")
print(f"模型压缩率: {teacher_params / student_params:.2f}x")
