# JMH 微基准测试
- 将 java-home 设为 java8

## Maven 测试
### maven-jmh-test
#### 生成项目
```
mvn archetype:generate -DinteractiveMode=false \
      -DarchetypeGroupId=org.openjdk.jmh -DarchetypeArtifactId=jmh-java-benchmark-archetype -DarchetypeVersion=1.19 \
      -DgroupId=cn.zxf.jmh -DartifactId=logging-demo -Dversion=1.0
```

#### 安装
```
mvn clean		// 清理
mvn compile		// 编译
mvn install		// 打包
```

#### 测试
- `java -jar target/benchmarks.jar -wi 5 -i 4 -t 3 -f 1`


---
## Gradle 测试
- jmh 类要放在 `src/jmh/java` 下面
- [jmh 插件参考](https://github.com/melix/jmh-gradle-plugin)
- 方法里面最好不用输出，如：`System.out.println( "hello" );`

### gradle-jmh-test
- `gradle jmh`


---
## 注解-说明
- [原文参考](http://www.jiangxinlingdu.com/practice/2019/06/05/jmh.html)

### @Benchmark
- 用来标记测试方法，只有被标记才会参与基准测试
- 方法必须是 `public`

### @Warmup
- 用来配置热身的内容。参数有：
- `iterations`：预热的次数
- `time`：每次预热的时间
- `timeUnit`：时间单位，默认是 s
- `batchSize`：批处理大小，每次操作调用几次方法

### @Measurement
- 用来控制实际执行的内容
- 选项和 `@Warmup` 一样

### @BenchmarkMode
- 表示测量的纬度，有以下：
- `Mode.Throughput` 吞吐量
- `Mode.AverageTime` 平均时间
- `Mode.SampleTime` 抽样检测
- `Mode.SingleShotTime` 检测一次调用
- `Mode.All` 运用所有的检测模式

### @OutputTimeUnit
- 代表测量的单位
- 一般使用微秒和毫秒级别

### @State
- 标识需要维护的状态内容
  - 即：指示多线程之间共不共享
- 有以下几种：
  - `Scope.Benchmark` 在所有的 Benchmark 的工作线程中共享
  - `Scope.Group` 同一个 Group 的线程可以享有同样的变量
  - `Scope.Thread` 每个线程都有一份变量的副本，线程之间互不影响
- `@Benchmark` 标注的方法可以有参数，但参数必须是被 `@State` 注解的

#### @State 钩子
- 必须标示在 `@State` 注解的类内部
- `@Setup` 表示初始化操作
- `@TearDown` 表示销毁操作
- 都提供了以下三种维度的控制：
  - `Level.Trial` 只会在测试的前后执行
  - - 包括 Warmup 和 Measurement 阶段，一共只会执行一次
  - `Level.Iteration` 每次执行测试方法时
  - - 如果 Warmup 和 Measurement 都配置了 2 次执行
  - - 那么 `@Setup` 和 `@TearDown` 方法执行 4 次
  - `Level.Invocation` 每个方法执行的前后执行（一般不推荐这么用）

### @Param
- 设置不同的参数

### @Threads
- 设置执行测试的线程数量

### @CompilerControl
- 控制方法能不能内联
- `CompilerControl.Mode.DONT_INLINE`：强制限制不能使用内联
- `CompilerControl.Mode.INLINE`：强制使用内联


---
## JMH进阶
- 1. 返回测试结果，防止编译器优化
  - 避免使用 `void` 方法
- 2. 通过 Blackhole 消费中间结果，防止编译器优化