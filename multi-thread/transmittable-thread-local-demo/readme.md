# transmittable-thread-local demo

## transmittable-thread-local
- 解决线程池中 `ThreadLocal` 数据的传递问题
- `TransmittableThreadLocal`、`TtlExecutors.getTtlExecutorService` 两者配合使用

## 原理
- `execute()`、`submit()` 方法执行 `TtlRunnable` 的封装
