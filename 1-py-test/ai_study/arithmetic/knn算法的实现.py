# 第2章/machineLearn/knn算法的实现.py
import math
import matplotlib.pyplot as plt

amount_ratio = 1000
# 历史相亲数据的描述
def historical_data():
    # 所有金额缩小到K为单位，方便计算
    data = {
        # 姓名：[年龄，收入，是否成功(0为失败，1为成功)]
        '文*': [25, 5000 / amount_ratio, 0],
        '祁*': [30, 20000 / amount_ratio, 1],
        '杨*1': [28, 11000 / amount_ratio, 0],
        '万*': [30, 40000 / amount_ratio, 1],
        '余*': [32, 6000 / amount_ratio, 0],
        '张*': [21, 7000 / amount_ratio, 0],
        '陈*': [27, 4000 / amount_ratio, 0],
        '利*': [26, 14000 / amount_ratio, 1],
        '杨*2': [34, 24000 / amount_ratio, 1],
        '钱*': [36, 7000 / amount_ratio, 0],
    }
    return data


def calculate_european_distance(input_x: list, data: dict):
    """
    返回欧式距离后的结果，并以[[人名,距离]]的格式返回
    :param input_x:
    :param data:
    :return:
    """
    distance_list = []
    for key, v in data.items():
        # 计算欧式距离
        d = math.sqrt((input_x[0] - v[0]) ** 2 + (input_x[1] - v[1]) ** 2)
        distance_list.append([key, round(d, 2)])
    # 对计算后的距离结果进行升序排列
    # 假设有10条数据，那么计算后的结果就是10条。即每一个数据都有一个距离
    return sorted(distance_list, key=lambda l: l[1])


def predict_ByK(distance_list, raw_data, k=5):
    # 确定前k个样本所在类别出现的频率，并输出出现频率最高的类别
    labels = {"相亲失败": 0, "相亲成功": 0}
    for s in distance_list[:k]:
        # 根据上一步距离计算出来的人名，从原始数据中取出样本信息
        # 即：[25, 5000 / amount_ratio, 0]
        sample_information = raw_data[s[0]]
        if sample_information[-1] == 0:
            labels['相亲失败'] += 1
        elif sample_information[-1] == 1:
            labels['相亲成功'] += 1
    # 根据相亲结果出现的次数进行升序排列。
    return sorted(labels.items(), key=lambda l: l[1], reverse=True)


def guess_k(lenged=0.4):
    """利用已有数据来对K值进行验证。"""
    raw_data = historical_data()
    data_keys = raw_data.keys()
    data_values = [[k] + v for k, v in raw_data.items()]
    # 通常说来取源数据的40%。
    data_values = data_values[0:int(len(data_keys) * lenged)]
    max_k = len(data_keys)
    verfiy_k = []
    verfiy_error = []
    verfiy_wrong = []
    for k in range(1, max_k, 2):
        error_count = 0
        guss_count = 0
        for d in data_values:
            label = '相亲失败' if d[-1] == 0 else '相亲成功'
            distance_list = calculate_european_distance(d[1:3], raw_data)
            result = predict_ByK(distance_list, raw_data, k=k)
            if label != result[0][0]:
                error_count += 1
            guss_count += 1
        verfiy_k.append(k)
        verfiy_error.append(error_count)
        verfiy_wrong.append(round(error_count / guss_count, 2))
    return verfiy_k, verfiy_error, verfiy_wrong


def plot_k_error(error, start=1, end=10, step=2):
    # 用来正常显示中文标签
    plt.rcParams['font.sans-serif'] = ['SimHei']
    plt.rcParams['axes.unicode_minus'] = False
    # 将x周的刻度线方向设置向内
    plt.rcParams['xtick.direction'] = 'in'
    # 将y轴的刻度方向设置向内
    plt.rcParams['ytick.direction'] = 'in'
    fig = plt.figure(num=10)
    ax1 = fig.add_subplot(111)
    ax1.plot(range(start, end, step), error[-1])
    ax1.set_xlabel(r'$K$')
    ax1.set_ylabel('错误率')
    for a, b in zip(range(start, end, step), error[-1]):
        plt.text(a, b, b, ha='center', va='bottom', fontsize=8)
    plt.show()

# 历史数据（训练数据）如果有归一化，预测数据也要同步进行归一化。归一化的方法要一样。
def test():
    raw_data = historical_data()
    input_x = ['小文', 26, 13000 / amount_ratio]
    distance_list = calculate_european_distance(input_x[1:], raw_data)
    result = predict_ByK(distance_list, raw_data, k=5)
    print(f'预测{input_x[0]}的相亲结果为：{result[0][0]}，\n '
          f'概率为：{round(result[0][1] / sum([y for x, y in result]), 2)}')


if __name__ == "__main__":
    # knn的算法过程
    test()
    # 测试，K取那一个值能够在验证集中效果最佳。数据较少，所以这里取所有
    # error = guess_k(lenged=1)
    # plot_k_error(error)
