# Bean 属性复制测试
- [原文参考](https://www.jianshu.com/p/f8b892e08d26)

## Apache BeanUtils 属性复制性能差
- 应使用 Spring `BeanUtils` 或 CGLib `BeanCopier` 来代替
- CGLib `BeanCopier` 的原理是生成 set 字节码