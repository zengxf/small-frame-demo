# JUnit 5 测试

## 原文参考
- https://juejin.im/post/5d80c66f51882539aa5adc10

## 注解-解释
- `@DisplayName` 设置名称
- `@BeforeAll` 和 `@AfterAll` 相当于 `@BeforeClass` 和 `@AfterClass`，只能修饰静态方法
- `@BeforeEach` 和` @AfterEach` 相当于 `@Before` 和 `@After`
- `@Disabled` 禁用执行测试，相当于 `@Ignore`
- `@Nested` 内嵌测试类
- `@RepeatedTest` 重复性测试

## 断言
- 使用 `Assertions` 类下的方法

## 参数化测试
- `@ParameterizedTest` 标注参数化测试方法
- `@ValueSource` 提供最简单的数据参数源
- `@CsvSource` CSV 数据源测试
- 其他的源注解：`@CsvFileSource` `@EnumSource` `@MethodSource` `@ArgumentSource`