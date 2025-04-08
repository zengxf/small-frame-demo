"""
异步上下文管理测试
"""

import asyncio


class AsyncContextManager:
    async def __aenter__(self):
        print("进入异步上下文")
        return self

    async def __aexit__(self, exc_type, exc, tb):
        print("退出异步上下文")


async def main():
    async with AsyncContextManager() as manager:
        print("在异步上下文中执行操作")


asyncio.run(main())
