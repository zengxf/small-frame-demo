"""
异步迭代测试
"""

import asyncio


class AsyncIterable:
    def __init__(self):
        self.count = 0

    def __aiter__(self):
        return self

    async def __anext__(self):
        if self.count < 3:
            await asyncio.sleep(1)
            self.count += 1
            return self.count
        else:
            raise StopAsyncIteration


async def main():
    async for number in AsyncIterable():
        print(f"数字: {number}")


asyncio.run(main())
