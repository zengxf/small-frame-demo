"""
文件操作
"""

# test_file = "../0test.md"
test_file = "0test.md"

print("\n****************************")
print("读取文件")
with open(test_file, "r") as file:
    content = file.read()
    print(content)

print("\n****************************")
print("写入文件")
with open(test_file, "w") as file:
    file.write("Hello, World!")
