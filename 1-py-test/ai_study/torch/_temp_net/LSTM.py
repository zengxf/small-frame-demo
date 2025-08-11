import torch
import torch.nn as nn


class CustomLSTMCell(nn.Module):
    def __init__(self, input_size, hidden_size):
        super().__init__()
        self.hidden_size = hidden_size

        # 初始化门控参数（权重和偏置）
        self.W_f = nn.Parameter(torch.randn(input_size + hidden_size, hidden_size) * 0.01)
        self.b_f = nn.Parameter(torch.zeros(hidden_size))
        self.W_i = nn.Parameter(torch.randn(input_size + hidden_size, hidden_size) * 0.01)
        self.b_i = nn.Parameter(torch.zeros(hidden_size))
        self.W_C = nn.Parameter(torch.randn(input_size + hidden_size, hidden_size) * 0.01)
        self.b_C = nn.Parameter(torch.zeros(hidden_size))
        self.W_o = nn.Parameter(torch.randn(input_size + hidden_size, hidden_size) * 0.01)
        self.b_o = nn.Parameter(torch.zeros(hidden_size))

    def forward(self, x, state):
        h_prev, C_prev = state  # 上一时刻的隐藏状态和记忆元
        combined = torch.cat((x, h_prev), dim=1)  # 拼接输入和隐藏状态

        # 计算遗忘门、输入门、输出门和候选记忆元
        f_t = torch.sigmoid(combined @ self.W_f + self.b_f)
        i_t = torch.sigmoid(combined @ self.W_i + self.b_i)
        o_t = torch.sigmoid(combined @ self.W_o + self.b_o)
        C_tilda = torch.tanh(combined @ self.W_C + self.b_C)

        # 更新记忆元和隐藏状态
        C_t = f_t * C_prev + i_t * C_tilda
        h_t = o_t * torch.tanh(C_t)

        return h_t, C_t

    def init_state(self, batch_size, device):
        """初始化隐藏状态和记忆元（全零）"""
        return (torch.zeros(batch_size, self.hidden_size, device=device),
                torch.zeros(batch_size, self.hidden_size, device=device))


class CustomLSTM(nn.Module):
    def __init__(self, input_size, hidden_size, output_size):
        super().__init__()
        self.lstm_cell = CustomLSTMCell(input_size, hidden_size)
        self.fc = nn.Linear(hidden_size, output_size)  # 输出层

    def forward(self, x):
        batch_size, seq_len, _ = x.shape
        device = x.device
        state = self.lstm_cell.init_state(batch_size, device)  # 初始化状态
        outputs = []

        # 按时间步迭代
        for t in range(seq_len):
            h_t, C_t = self.lstm_cell(x[:, t, :], state)
            state = (h_t, C_t)  # 更新状态
            outputs.append(h_t)

        # 取最后一步输出并分类/回归
        last_output = outputs[-1]
        return self.fc(last_output)


# 生成数据（序列预测）
def generate_data(seq_length=10, n_samples=1000):
    t = torch.linspace(0, 10, n_samples)
    data = torch.sin(t) + torch.randn(n_samples) * 0.1  # 正弦波+噪声
    X, y = [], []
    for i in range(n_samples - seq_length):
        X.append(data[i:i + seq_length].unsqueeze(-1))
        y.append(data[i + seq_length])
    return torch.stack(X), torch.stack(y).unsqueeze(-1)


# 训练设置
model = CustomLSTM(input_size=1, hidden_size=50, output_size=1)
criterion = nn.MSELoss()
optimizer = torch.optim.Adam(model.parameters(), lr=0.01)

# 训练循环
X, y = generate_data()
for epoch in range(10):
    optimizer.zero_grad()
    output = model(X)
    loss = criterion(output, y)
    loss.backward()
    torch.nn.utils.clip_grad_norm_(model.parameters(), max_norm=1.0)  # 梯度裁剪[8](@ref)
    optimizer.step()
    print(f"Epoch {epoch}, Loss: {loss.item():.4f}")
