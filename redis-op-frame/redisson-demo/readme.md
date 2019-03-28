## 简介
- 基于 Redis 实现 集合、原子数据、锁
- GitHub: https://github.com/redisson/redisson [/wiki]

## 源码分析
### 一部分
- 连接在初始化 `Redisson` 时，会初始化池 `ConfigSupport.createConnectionManager(xx)`
- 获取连接返回 `RFuture`，可添加监听器，在监听器里面执行命令 `CommandAsyncService.async(xx)`
- 发送命令 `CommandAsyncService.sendCommand(xx)`，在连接的 `RFuture` 监听器中调用
- 关闭连接方法 `CommandAsyncService.releaseConnection(xx)`，也是，相当于命令发送完后就关闭
- `sendCommand(xx)` 方法会传递 `getAttemptPromise()` 给命令，命令的处理会进一步设置 `RPromise` 的结果

### 源码分析反思
- 一是逆向查看调用栈 `Ctrl+H`
- 二是清楚 JDK 中 API 的原理
- 三是间断阅读（如隔个一、两天）
- 四、像 netty 不熟，则发送命令的具体实现可以忽略

## 功能
### 锁
- Lock、RedLock

### RPC
- RRemoteService

### 对象
- key 相关操作 -- RKeys
- 通用对象桶（RBucket）
- 二进制流（RBinaryStream）
- 地理空间对象桶（RGeo）
- BitSet（RBitSet）
- 原子整长形（RAtomicLong）
- 原子双精度浮点（RAtomicDouble）
- 整长型累加器（RLongAdder）
- 双精度浮点累加器（RDoubleAdder）
- 话题（订阅分发）（RTopic）
- 模糊话题（RPatternTopic）
- 布隆过滤器（RBloomFilter）
- 基数估计算法（RHyperLogLog）
- 限流器（RRateLimiter）

----
---
### 集合
#### Map
##### 映射（Map）
- 有对应的 key 的互斥锁和读写锁

##### RMapCache
- 可以设置元素淘汰

##### 本地缓存功能（RLocalCachedMap）
- 读取频繁的放在 JVM 内存中

##### 映射持久化方式（缓存策略）
- Read-through 策略
  - 数据不存在映射中，Redisson 将通过预先配置好的 MapLoader 对象加载数据
- Write-through（数据同步写入）策略
  - 数据被更改时，Redisson 先通过预先配置好的 MapWriter 对象写入到外部储存系统，然后再更新 Redis 内的数据
- Write-behind（数据异步写入）策略
  - 对映射的数据的更改会首先写入到 Redis
  - 然后再使用异步的方式，通过 MapWriter 对象写入到外部储存系统
  - 可以通过 writeBehindThreads 参数来控制写入线程的数量

##### 映射监听器（Map Listener）
- 元素 添加 事件
- 元素 过期 事件
- 元素 删除 事件
- 元素 更新 事件

##### LRU 有界映射
- `trySetMaxSize()`、`setMaxSize()` 设置限制


#### 多值映射（MultiMap）
- 基于集（Set）的多值映射（RSetMultimap）
- 基于列表（List）的多值映射（RListMultimap）
- 可淘汰的多值映射（RSetMultimapCache）
  - `expireKey(x, x, x)`

#### 集（Set）
- RSet
- 淘汰机制（RSetCache）
- 有序集（RSortedSet）
- 计分排序集（RScoredSortedSet）
- 字典排序集（RLexSortedSet）

#### 列表&队列
- 列表（RList）
- 队列（RQueue）
- 双端队列（RDeque）
- 阻塞队列（RBlockingQueue）
- 有界阻塞队列（RBoundedBlockingQueue）
- 阻塞双端队列（RBlockingDeque）
- 延迟队列（RDelayedQueue）
- 优先队列（RPriorityQueue）
- 优先双端队列（RPriorityDeque）
- 优先阻塞队列（RPriorityBlockingQueue）
- 优先阻塞双端队列（RPriorityBlockingDeque）