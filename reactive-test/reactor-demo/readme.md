# Reactor demo
- [原文参考](https://www.ibm.com/developerworks/cn/java/j-cn-with-reactor-response-encode/index.html)

## 介绍
### Flux 和 Mono
#### 反应式编程介绍
- `c = a + b`，当 a 或者 b 的值发生变化时
- 传统编程范式需要对 `a + b` 进行重新计算来得到 c
- 反应式编程，当 a 或者 b 的值发生变化时，c 的值会自动更新
- 传统的编程范式中，由调用者控制节奏，采用的是拉的方式
- 反应式流采用推的方式，即常见的发布者-订阅者模式

#### RxJava VS Reactor
- RxJava 产生于反应式流规范之前
  - 虽然可以和反应式流的接口进行转换，但是由于底层实现的原因，使用起来并不是很直观
- RxJava 2 在设计和实现时考虑到了与规范的整合
  - 不过为了保持与 RxJava 的兼容性，很多地方在使用时也并不直观
- Reactor 则是完全基于反应式流规范设计和实现的库
  - 在使用上更加的直观易懂
  - 也是 Spring 5 中反应式编程的基础

#### Flux 和 Mono
- 是 Reactor 中的两个基本概念
- Flux 表示 0 到 N 个元素的异步序列
  - 包含三种消息通知：正常的包含元素的消息、序列结束的消息和序列出错的消息
  - 其订阅者对应的方法是 `onNext()`, `onComplete()` 和 `onError()`
- Mono 表示 0 或者 1 个元素的异步序列
  - 该序列与 Flux 相同的三种类型的消息通知
- Flux 和 Mono 之间可转换
  - 对一个 Flux 序列进行计数操作，结果是 Mono<Long> 对象
  - 把两个 Mono 序列合并，得到的是一个 Flux 对象

#### Flux 简单创建
- just()：指定序列中包含的全部元素 
  - 创建出来的 Flux 序列在发布这些元素之后会自动结束
- fromArray()，fromIterable()和 fromStream()：从数组、Iterable 或 Stream 创建 Flux 对象
- empty()：创建一个不包含任何元素，只发布结束消息的序列
- error(Throwable error)：创建一个只包含错误消息的序列
- never()：创建一个不包含任何消息通知的序列
- range(int start, int count)：创建包含从 start 起始的 count 个数量的 Integer 对象的序列
- interval(Duration period)和 interval(Duration delay, Duration period)：
  - 创建一个包含了从 0 开始递增的 Long 对象的序列
  - 指定间隔发布；指定延迟时间

#### Flux generate() 方法
- 通过同步和逐个的方式来产生 Flux 序列
- 序列的产生是通过调用提供的 `SynchronousSink` 对象的 `next(t)`、`complete()` 和 `error(e)`
  - 通过 `complete()` 结束序列
  - 可通过传递状态（stateSupplier）用于判断结束
- 一次调用只能产生一个，即不能调用多次 `next(t)`

#### Flux create() 方法
- 与 generate()方法的不同是使用 FluxSink 对象
- FluxSink 支持同步和异步的消息产生，并且可以在一次调用中产生多个元素

#### Mono 常用方法
- 与 Flux 比较相似，有 `just()`、`empty()`、`error()` 和 `never()` 等
- fromXX()：从 XX 中创建 Mono
- delay(Duration duration)：创建一个 Mono 序列，在指定的延迟时间之后，产生数字 0 作为唯一值
- ignoreElements(Publisher<T> source)：创建一个 Mono 序列
  - 忽略作为源的 Publisher 中的所有元素，只产生结束消息
- justOrEmpty(T)：只有对象不为 null 时，Mono 序列才产生对应的元素
- create(C)：使用 MonoSink 来创建 Mono

#### Flux 操作符 buffer(t)
- 把当前流中的元素收集到集合（List）中，并把集合对象作为流中的新元素
- `bufferTimeout(m, t)` 设置元素数量和时间间隔
- `bufferUntil(p)` 会一直收集直到 Predicate 返回为 true
- `bufferWhile(p)` 只有当 Predicate 返回 true 时才收集
  - 一旦值为 false，会立即开始下一次收集

#### Flux 操作符 filter(p)
- 对元素过滤，只留下满足指定条件的元素

#### Flux 操作符 window(m)
- 作用类似于 `buffer(m)`
- 不同的是 window 操作符是把当前流中的元素收集到另外的 Flux 序列中
  - 因此返回值类型是 Flux<Flux<T>>

#### Flux 操作符 zipWith(f)
- 把当前流中的元素与另外一个流中的元素按照一对一的方式进行合并
- 合并时不做处理，得到的是一个元素类型为 Tuple2 的流
- 也可通过 BiFunction 函数对合并的元素进行处理，所得到的流的元素类型为该函数的返回值
- 两个流数据不相等时，以最小数据的流为基准，不会报错

#### Flux 操作符 take(s)
- 从当前流中提取元素
- `take(n)`，`take(timeSpan)`：按照指定的数量或时间间隔来提取
- `takeLast(n)`：提取流中的最后 N 个元素
- `takeUntil(predicate)`：提取元素直到 predicate 返回 true
- `takeWhile(predicate)`： 当 predicate 返回 true 时才进行提取
- `takeUntilOther(Publisher<?> other)`：提取元素直到另外一个流开始产生元素

#### Flux 操作符 reduce(bf)
- 对流中所有元素进行累积操作，得到一个 Mono 序列
- 累积操作通过 BiFunction 完成
- 可以指定一个初始值。如果没有，则用第一个元素作初始值

#### Flux 静态操作符 merge(ss)
- 把多个流合并成一个 Flux 序列
- `merge()` 按照所有流中元素的实际产生顺序来合并
- `mergeSequential()` 按照所有流被订阅的顺序，以流为单位进行合并

#### Flux 操作符 flatMap(f)
- 把流中的每个元素转换成一个流，再把所有流中的元素进行合并
- `flatMapSequential()` 以顺序进行，类似 `mergeSequential()`

#### Flux 操作符 concatMap(f)
- 把流中的每个元素转换成一个流，再把所有流进行合并
- 与 `mergeSequential()` 类似
  - `concatMap()` 对转换之后的流的订阅是动态进行的
  - 而 `flatMapSequential()` 在合并之前就已经订阅了所有的流
- `concatWith()` 顺序拼接接

#### Flux 操作符 combineLatest(f)
- 把所有流中的最新产生的元素合并成一个新的元素
- 只要其中任何一个流中产生了新的元素
  - 合并操作就会被执行一次，产生新的元素

#### Flux 错误处理 
- `subscribe()` 处理正常和错误消息
- `onErrorReturn()` 出现错误时返回默认值
- `onErrorResume()` 出现错误时根据异常类型来返回流
- `retry()` 出现错误时进行重试
  - 重试的动作是通过重新订阅序列来实现的
  - 相当于全部重新运行

#### Reactor 调度器
- 创建方式集中在 `Schedulers`
- `immediate()` 当前线程
- `single()` 单一的可复用的线程
- `elastic()` 使用弹性线程池，适用于 I/O 操作，相当于 JUC 下的 cached 线程池
- `parallel()` 使用对并行操作优化的线程池，线程数取决于 CPU，适用于计算密集型
- `fromExecutorService()` 从已有的创建

#### Flux 切换调度器
- `publishOn()` 切换操作符的执行方式，相当于加工
- `subscribeOn()` 切换产生元素时的执行方式，相当于初始化

#### Reactor 测试套件
- 需要添加 `reactor-test` 依赖
- 使用 `StepVerifier` 进行期望验证
  - `expectNext()` 期待下一个元素的值
  - `verifyComplete()` 验证是否正常结束
  - `verifyError()` 验证流由于错误而终止
- `withVirtualTime()` 创建出使用虚拟时钟的 `StepVerifier`
  - `thenAwait()` 让虚拟时钟前进
  - `expectNoEvent()` 验证在指定时间段内没有任何消息产生 
- 使用 `TestPublisher` 控制流中元素的产生

#### Reactor 调试操作符
- `log()` 仅对上一个操作符进行日志记录
- `checkpoint()` 添加检查点，当出错时，检查点会出现在异常堆栈信息中，应加在最后

#### “冷”与“热”序列
- 冷序列：不论订阅者何时订阅，总能收到序列中产生的全部消息
- 热序列：订阅者只能获取订阅之后产生的消息
- `publish()` 把一个 Flux 对象转换成 ConnectableFlux 对象
- `autoConnect()` 作用是当 ConnectableFlux 有订阅者时才开始产生消息
- `subscribe()` 作用是订阅该 ConnectableFlux 对象，让其开始产生数据