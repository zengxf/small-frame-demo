# Bean 属性复制测试
- [原文参考](https://www.jianshu.com/p/f8b892e08d26)

## Apache BeanUtils 属性复制性能差
- 应使用 Spring `BeanUtils` 或 CGLib `BeanCopier` 来代替
- CGLib `BeanCopier` 的原理是生成 set 字节码

## Dozer 复制
- 原文参考：https://www.jianshu.com/p/7ef49f54b59b
- 会自动进行属性的类型转换，集合的泛型转换
- 遇见 `String` 转 `Integer` 时，格式不对则会报错
- 可以对 `Object` 和 `Map` 进行相互转换