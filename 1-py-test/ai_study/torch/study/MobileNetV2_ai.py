"""
https://www.kimi.com/chat/d27hcja5hvllsf02hdtg
"""

import torch
import torch.nn as nn
from typing import List, Optional, Callable


# 1.1 倒残差模块
class InvertedResidual(nn.Module):
    def __init__(self,
                 in_ch: int,
                 out_ch: int,
                 stride: int,
                 expand_ratio: int,
                 norm_layer: Optional[Callable[..., nn.Module]] = None):
        super().__init__()
        assert stride in (1, 2)
        if norm_layer is None:
            norm_layer = nn.BatchNorm2d

        hidden = int(round(in_ch * expand_ratio))
        self.use_res = stride == 1 and in_ch == out_ch

        layers: List[nn.Module] = []
        if expand_ratio != 1:  # 1×1 升维
            layers.append(
                self._conv_bn_act(in_ch, hidden, 1, 1, 0, norm_layer, nn.ReLU6)
            )
        # 3×3 depthwise
        layers.append(
            self._conv_bn_act(hidden, hidden, 3, stride, 1, norm_layer,
                              nn.ReLU6, groups=hidden)
        )
        # 1×1 降维（线性）
        layers.append(
            nn.Conv2d(hidden, out_ch, 1, 1, 0, bias=False)
        )
        layers.append(norm_layer(out_ch))

        self.conv = nn.Sequential(*layers)

    @staticmethod
    def _conv_bn_act(in_c, out_c, k, s, p, norm, act, **kwargs):
        return nn.Sequential(
            nn.Conv2d(in_c, out_c, k, s, p, bias=False, **kwargs),
            norm(out_c),
            act(inplace=True)
        )

    def forward(self, x: torch.Tensor) -> torch.Tensor:
        if self.use_res:
            return x + self.conv(x)
        return self.conv(x)


# 1.2 MobileNet-V2 主体
class MobileNetV2(nn.Module):
    def __init__(self,
                 num_classes: int = 1000,
                 width_mult: float = 1.0,
                 round_nearest: int = 8,
                 norm_layer: Optional[Callable[..., nn.Module]] = None):
        super().__init__()
        if norm_layer is None:
            norm_layer = nn.BatchNorm2d

        def _make_divisible(ch, divisor=round_nearest):
            return int(ch + divisor / 2) // divisor * divisor

        # 倒残差配置： (expand_ratio, channels, num_blocks, stride)
        inverted_residual_setting = [
            [1, 16, 1, 1],
            [6, 24, 2, 2],
            [6, 32, 3, 2],
            [6, 64, 4, 2],
            [6, 96, 3, 1],
            [6, 160, 3, 2],
            [6, 320, 1, 1],
        ]

        input_channel = _make_divisible(32 * width_mult)
        last_channel = _make_divisible(1280 * max(1.0, width_mult))

        # 首层
        features: List[nn.Module] = [
            self._conv_bn_act(3, input_channel, 3, 2, 1, norm_layer, nn.ReLU6)
        ]

        # 倒残差堆叠
        for t, c, n, s in inverted_residual_setting:
            output_channel = _make_divisible(c * width_mult)
            for i in range(n):
                stride = s if i == 0 else 1
                features.append(
                    InvertedResidual(input_channel, output_channel, stride, t, norm_layer)
                )
                input_channel = output_channel

        # 1×1 卷积
        features.append(
            self._conv_bn_act(input_channel, last_channel, 1, 1, 0, norm_layer, nn.ReLU6)
        )

        self.features = nn.Sequential(*features)
        self.avgpool = nn.AdaptiveAvgPool2d(1)
        self.classifier = nn.Sequential(
            nn.Dropout(0.2),
            nn.Linear(last_channel, num_classes),
        )

        self._init_weights()

    @staticmethod
    def _conv_bn_act(in_c, out_c, k, s, p, norm, act):
        return nn.Sequential(
            nn.Conv2d(in_c, out_c, k, s, p, bias=False),
            norm(out_c),
            act(inplace=True)
        )

    def _init_weights(self):
        for m in self.modules():
            if isinstance(m, nn.Conv2d):
                nn.init.kaiming_normal_(m.weight, mode='fan_out')
                if m.bias is not None:
                    nn.init.zeros_(m.bias)
            elif isinstance(m, (nn.BatchNorm2d, nn.GroupNorm)):
                nn.init.ones_(m.weight)
                nn.init.zeros_(m.bias)
            elif isinstance(m, nn.Linear):
                nn.init.normal_(m.weight, 0, 0.01)
                nn.init.zeros_(m.bias)

    def forward(self, x: torch.Tensor) -> torch.Tensor:
        x = self.features(x)
        x = self.avgpool(x)
        x = torch.flatten(x, 1)
        x = self.classifier(x)
        return x


# 1.3 构建函数（兼容 torchvision 调用习惯）
def mobilenet_v2(num_classes: int = 1000, pretrained: bool = False):
    model = MobileNetV2(num_classes=num_classes)
    if pretrained:
        # 官方权重
        state_dict = torch.hub.load_state_dict_from_url(
            "https://download.pytorch.org/models/mobilenet_v2-b0353104.pth",
            progress=True
        )
        model.load_state_dict(state_dict, strict=False)
    return model


if __name__ == "__main__":
    net = mobilenet_v2(pretrained=True)  # 也可设为 False 从零开始
    net.eval()
    x = torch.randn(1, 3, 224, 224)
    y = net(x)
    print("output shape:", y.shape)  # [1, 1000]

# # 一行调用 torchvision 官方实现（可选）
# import torchvision, torch
# model = torchvision.models.mobilenet_v2(weights='IMAGENET1K_V1')
# model.eval()

# 一行调用 torchvision 官方实现（手动下载权重）
# url: https://download.pytorch.org/models/mobilenet_v2-b0353104.pth
import torchvision, torch
model = torchvision.models.mobilenet_v2(pretrained=False)
model.load_state_dict(torch.load('./mobilenet_v2-b0353104.pth'))
model.eval()


