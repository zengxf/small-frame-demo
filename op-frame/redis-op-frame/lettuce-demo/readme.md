## 简介
- 连接 Redis Server 的客户端程序
- GitHub: https://github.com/lettuce-io/lettuce-core

## Lettuce VS Jedis
- Jedis 在实现上是直连 redis server
  - 多线程环境下非线程安全，除非使用连接池，为每个Jedis实例增加物理连接
- Lettuce 基于 Netty 的连接实例（StatefulRedisConnection）
  - 可以在多个线程间并发访问，且线程安全，满足多线程环境下的并发访问
  - 同时它是可伸缩的设计，一个连接实例不够的情况也可以按需增加连接实例