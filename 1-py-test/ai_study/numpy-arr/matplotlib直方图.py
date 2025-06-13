'''matplotlib 第三方模块，需要安装。可视化，画图：直方图，柱状图，散点图，饼图等 '''

''' 终端：pip install matplotlib -i https://pypi.tuna.tsinghua.edu.cn/simple/ '''

# 导入模块
import matplotlib.pyplot as plt

''' 创建画板：plt.figure(num=名称, figsize=(宽, 高), dpi=像素, facecolor=背景色)  '''

# f1 = plt.figure(num='画板', figsize=(32,32), dpi=10, facecolor='red')
# print(type(f1))

# plt.show()

''' 画图常用的属性：设置标题，x，y轴的描述，x,y轴的刻度，图例名称等，显示图片，保存 '''
# plt.show()      # 显示图形
# plt.xlabel()    # x轴名称
# plt.ylabel()    # y轴名称
# plt.xlim()      # x轴范围
# plt.ylim()      # y轴范围
# plt.legend()    # 图例名称的位置：loc= upper right, lower left
# plt.grid(True)        # 网格图
#
# plt.xticks()    # x轴刻度
# plt.yticks()    # y轴刻度


'''
plt.rcParams['font.sans-serif'] = ['SimHei']	# 解决中文显示问题
plt.rcParams['axes.unicode_minus'] = False	    # 解决负号显示问题
'''

''' 1. 折线图：
plt.plot(x, y, color, linestyle, linewidth, marker, markersize, label) 
# linestyle 线条：实线（-）、虚线（--）、点线（:）、破折线（-.)
# marker 标记：点（.）、圆圈（o）、叉号（x）、方框（s）、三角形（^）
# label 函数、公式
'''
plt.rcParams['font.sans-serif'] = ['SimHei']	# 解决中文显示问题
plt.rcParams['axes.unicode_minus'] = False
x = [1,2,3,4,5]
y = [1,4,9,16,25]
z = [6,7,8,9,10]
#
# plt.plot(x,y,z, color='red',linestyle='--',linewidth=2, marker='s',markersize=5, label='y=x^2')
#
# plt.title('折线图')
# plt.xlabel('数字', loc='right')       # left, center, right
# plt.ylabel('平方', loc='top')         # bottom, center, top
# plt.xlim([1, 10])
# plt.xticks([1,3,5,7,9])
# plt.ylim([1, 100])
# plt.yticks([1,3,5,7,9])
# plt.legend(loc='upper right')               # upper right, lower left
# plt.show()


''' 2. 直方图 ：
plt.hist(data, bins, range, density, cumulative, color, edgecolor, alpha, log, label) '''
import numpy as np
# plt.figure('直方图', figsize=(50,80),facecolor='red')
# data = np.random.randn(10000)

plt.hist(np.random.randn(1000), bins=30, cumulative=False, color='skyblue', edgecolor='red')
plt.title('标准的正态分布')
plt.xlabel('区间')
plt.ylabel('频率')
plt.show()


''' 3. 保存图片 
基本格式：plt.savefig(fname, dpi, bbox_inches, format, transparent, pad_inches)
常用的参数如下：
fname 文件名：保存图像的文件名或路径，通常带文件扩展名
dpi 分辨率：决定保存图像的清晰度，数值越大，图像越清晰。通常使用 300 、 600
bbox_inches 裁剪边界框：控制图像的边界区域，使用 tight 可以去掉图像周围的空白区域
format 文件格式：指定保存文件的格式，如 png, jpg, pdf 等
transparent 透明背景：如果设置为 true，图像的背景将为透明
pad_inches 边距填充：控制图像和边框之间的距离，常与 bbox_inches='tight' 一起使用，确保图像不被过度裁剪
'''
