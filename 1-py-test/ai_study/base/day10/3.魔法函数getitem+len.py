# 魔法函数 getitem、setitem、delitem  + len 使用，使对象兼容 列表、字典的使用
''' getitem 结合 len 一起使用 '''
''' 1. getitem : 使对象兼容列表和字典的索引访问 '''
# 列表 [start: stop: step]
# 字典 [键]
class MagicGetitem:

    def __init__(self, 形参):
        self.实例属性 = 形参

    def __len__(self):
        return len(self.实例属性)

    def __getitem__(self, item):
        if isinstance(item, slice):
            return self.实例属性[item.start : item.stop : item.stop]
        else:
            return self.实例属性[item]


# 实例对象，对象兼容列表或字典的索引访问功能
data = 'python'
l = MagicGetitem(data)
# l[3] 和 l[2:] 切片
print(l[3])
print(l[2:])
print("--2"*20)




''' 2. setitem : 使对象兼容列表和字典的索引修改 '''
class MagicSetitem:

    def __init__(self, 形参):
        self.实例属性 = 形参

    def __len__(self):
        return len(self.实例属性)

    def __getitem__(self, x):            # 访问
        if isinstance(x, slice):
            return self.实例属性[x.start : x.stop : x.stop]
        else:
            return self.实例属性[x]

    def __setitem__(self, key, value):      # 修改
        if isinstance(key, dict):
            self.实例属性[key] = value
        elif isinstance(key, slice):
            self.实例属性[key.start : key.stop : key.step] = value

data = [1,2,3,4,5]
m = MagicSetitem(data)
m[3] = 6
print(m[:])
m[1:4] = [7]
print(m[:])
