# Java 19 相关测试
- 启用预览版（IDEA 和 Gradle 都要设置）：
  - https://blog.csdn.net/baojianhuangbo/article/details/106093526
  - https://www.imlc.me/Intellij-IDEA-开启-JDK-预览特性/
- Gradle 使用 8.0 

## 虚拟线程
虚拟线程的存在是为了提供更高的吞吐量，而不是速度（更低的延迟）。

如果你的应用程序符合下面两点特征，使用虚拟线程可以显著提高程序吞吐量：

程序并发任务数量很高。
IO密集型、工作负载不受 CPU 约束。
虚拟线程有助于提高服务端应用程序的吞吐量，因为此类应用程序有大量并发，而且这些任务通常会有大量的 IO 等待。

从 sleep 中返回时，继续在线程 4 上运行。

由于虚拟线程可以创建数百万个，在虚拟线程中使用 ThreadLocal 请三思而后行。