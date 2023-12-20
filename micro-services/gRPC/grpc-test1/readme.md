## 参考

- https://juejin.cn/post/6844904115248562189
- https://www.baeldung.com/java-grpc-streaming

## 问题

- 无法解析 `@javax.annotation.Generated`
    - https://www.jianshu.com/p/a2e479752d49
    - https://blog.csdn.net/ml863606/article/details/109202246

## 安装

```shell
# 之前生成的有问题的类，需要清理下
# 然后再重新构建，生成 gRPC 服务类
gradle clean
gradle build
```