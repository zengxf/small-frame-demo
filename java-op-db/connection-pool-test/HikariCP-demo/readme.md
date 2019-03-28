# HikariCP demo
- 高性能数据库连接池
- 参考：https://github.com/brettwooldridge/HikariCP/wiki/Down-the-Rabbit-Hole
- 使用 `FastList` 替代 ArrayList，无检测 get()
- `ConcurrentBag` 更好的并发集合类实现
- 优化并精简字节码，字节码优化创建代理：`ProxyFactory.getProxyConnection()`