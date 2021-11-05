## Guava 测试

#### Guava 限流器
- 解释一般：https://juejin.cn/post/6844903783432978439
- 解释的好些：https://segmentfault.com/a/1190000023333903
- 使用**令牌桶算法**
  - `storedPermits` 表明当前令牌桶中有多少令牌
  - `maxPermits` 表示令牌桶最大令牌数目
  - `storedPermits` 的取值范围为：`[0, maxPermits]`
  - `stableIntervalMicros` 等于 `1/qps`，它代表系统在稳定期间，两次请求之间间隔的微秒数
    - 例如：`qps` 为 `5`，则 `stableIntervalMicros` 为 `200ms`
  - `nextFreeTicketMicros` 表示系统处理完当前请求后
  - 下一次请求被许可的最短微秒数，如果在这之前有请求进来，则必须等待
- **惰性计算**，不执行**阻塞**
  - 也不开启后台任务
- `SmoothBursty` 令牌的生成速度恒定
- `SmoothWarmingUp` 令牌的生成速度持续提升，直到达到一个稳定的值
  - 存在一个“热身”的概念
  - **热身**阶段等待的时间会长些