# Guice 测试

- 原文参考：https://www.jianshu.com/p/a648322dc680
- 原文参考：https://juejin.im/post/5a375e156fb9a0452a3c6b96

## 总结
- 可以不进行任何配置，直接获取实例
  - `UserService service = injector.getInstance(UserService.class);`