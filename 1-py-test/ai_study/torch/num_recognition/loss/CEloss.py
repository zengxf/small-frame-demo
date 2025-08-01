import torch
import torch.nn as nn
import torch.nn.functional as F


class CEloss(nn.Module):
    def __init__(self):
        super(CEloss, self).__init__()

    def forward(self, pre_out, labels):
        labels = F.one_hot(labels, num_classes=10).float()
        loss = torch.sum(torch.mean(-(labels * torch.log(pre_out) + (1 - labels) * torch.log(1 - pre_out)), dim=1))
        return loss


if __name__ == '__main__':
    torch.manual_seed(88)

    x = torch.randn((1, 10))
    print('x: ', x)

    pre_out = F.softmax(x, dim=1)
    print('pre_out: ', pre_out)

    labels = torch.randint(0, 10, (1, 1))
    print('labels: ', labels)

    loss = CEloss()
    loss_val = loss(pre_out, labels)
    print(loss_val)
