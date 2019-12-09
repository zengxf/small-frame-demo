## 生产者-消费者 模式

### 生产者
```
# RingBuffer 初始化时，会利用 EventFactory 初始化 entries 数组
 
long sequence = ringBuffer.next(); // 获取位置
LongEvent e = ringBuffer.get( sequence ); // 获取位置的事件
e.set( value ); // 设置值 
ringBuffer.publish( sequence ); // 发布
```

### 消费者
```
# 最终是 WorkProcessor 类的 run() 在处理
# WorkerPool 的 start() 启动消费者线程
ringBuffer.get(nextSequence); // 获取
```

### 总结
```
# 核心代码
生产：sequencer.next()
消费：sequence.get()
判断是否可用：
	if (cachedAvailableSequence >= nextSequence) {
		// 数据可用
	}
	cachedAvailableSequence = sequenceBarrier.waitFor(nextSequence); // 通过屏障来处理
	waitStrategy.waitFor(sequence, cursorSequence, dependentSequence, this); // 最终调用策略的等待获取
	# 最终还是通过序号判断
```

## 资料参考
- [一篇对伪共享、缓存行填充和CPU缓存讲的很透彻的文章](https://blog.csdn.net/qq_27680317/article/details/78486220)