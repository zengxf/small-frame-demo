"""
简单测试
"""

import asyncio


async def fetch_data():
    print("------------------- 1")
    await asyncio.sleep(1.5)    # 单位：秒
    print("------------------- 2")
    return "data"


async def main():
    print("main -------------- 0")
    data = await fetch_data()
    print("fetch-data:", data)


asyncio.run(main())
