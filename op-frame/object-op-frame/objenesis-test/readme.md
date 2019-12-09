# Objenesis 测试
- **Objenesis** 用来实例化特定类的对象
- Stardard：没有构造器会被调用。类：`ObjenesisStd`
- Serilizable compliant：与 java 标准序列化方式实例一个对象的行为一致
  - 类：`ObjenesisSerialializer`
  - 目标类要实现 `Serializable` 接口


## 原理
- 最终使用 `sun.reflect.ReflectionFactory`

### 类似代码
```
ReflectionFactory.getReflectionFactory()
  .newConstructorForSerialization( Xx.class )
  .newInstance( (Object[]) null );
```


## 参考
- [Objenesis 的基本使用方法](https://blog.csdn.net/codershamo/article/details/52015206)