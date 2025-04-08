"""
协程简单测试 (协程函数)
"""

import asyncio


# 定义一个协程函数
# 使用 async def 定义一个协程函数
async def greet(name, delay):
    print(f"开始问候 {name}，等待 {delay} 秒")
    await asyncio.sleep(delay)  # 模拟异步操作，例如网络请求或文件读取
    # asyncio.sleep(delay)      # 需要 await 操作
    print(f"结束问候 {name}")


# 主协程函数
async def main():
    # 创建多个协程任务
    task1 = greet("Alice", 2)
    task2 = greet("Bob", 3)
    task3 = greet("Charlie", 1)

    # 使用 asyncio.gather 并行执行多个协程
    # asyncio.gather 会等待所有传入的协程完成。
    await asyncio.gather(task1, task2, task3)


# 运行主协程
if __name__ == "__main__":
    # asyncio.run 启动事件循环并运行主协程 main。
    asyncio.run(main())  # 正常运行
    # await main()  # 语法错误
    # main()        # 运行异常
