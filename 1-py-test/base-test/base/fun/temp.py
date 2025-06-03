''' 5. max|min(可迭代对象, key=lambda) 最大,最小 '''
list_data = [{'name': '宁采臣', 'age': 25, 'salary': 6000},
             {'name': '聂小倩', 'age': 24, 'salary': 4000},
             {'name': '燕赤霞', 'age': 32, 'salary': None},
             {'name': '姥姥', 'age': 45, 'salary': 8000},
             {'name': '黑山老妖', 'age': 45, 'salary': 8000}]

# 找出薪资最大的用户信息, max 返回的是 一个
print(max(list_data, key=lambda d: d['salary'] if d['salary'] is not None else float('-inf')))

print(list(filter(lambda d: d['salary'] == max(
    i['salary'] for i in list_data if i['salary'] is not None
), list_data)))

s = 'abcd5#aa2dasd@dsd0%'
print(int(''.join(list(filter(lambda x:x.isdigit(), s)))))


# 5. 使用 lambda，给第 4 题的字典列表中，每个用户增加新的键 job，值为 job:ai
list_data = [{'name': '宁采臣', 'age': 25, 'salary': 6000},
             {'name': '聂小倩', 'age': 24, 'salary': 4000},
             {'name': '燕赤霞', 'age': 32, 'salary': None},
             {'name': '姥姥', 'age': 45, 'salary': 8000},
             {'name': '黑山老妖', 'age': 45, 'salary': 8000}]

# 初始化计数器
max_call_count = 0

# 定义一个包装函数来计数
def get_max(salary_iterable):
    global max_call_count
    max_call_count += 1
    return max(salary_iterable)

# 修改原始代码，使用包装函数
# max_salary = get_max(i['salary'] for i in list_data if i['salary'] is not None)
print(list(filter(lambda d: d['salary'] == get_max(i['salary'] for i in list_data if i['salary'] is not None), list_data)))
print(f"max 函数被调用了 {max_call_count} 次")
