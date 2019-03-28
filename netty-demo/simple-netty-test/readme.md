# Netty demo

## 参考
- [原文参考 1](https://www.jianshu.com/p/b9f3f6a16911)
- [原文参考 2](https://www.jianshu.com/p/ed0177a9b2e3)
- [Netty 实战(精髓)](https://waylau.gitbooks.io/essential-netty-in-action/content/)

## 简单概念
### OIO、NIO
- OIO：Old-IO，即阻塞式 IO
- NIO: New-IO，即非阻塞 IO

### Channel - 数据传输流
- `Channel` 表示一个连接，可以理解为每一个请求，就是一个 Channel
  - 一个客户端与服务器通信的通道
- `ChannelHandler` 业务逻辑处理器，处理业务请求
  - 分为 `ChannelInboundHandler`（输入数据处理器）和 `ChannelOutboundHandler`（输出数据处理器）
  - 自带注解 `@Sharable` 用于标记实例可以在 channel 里共享
  - 以添加时的顺序执行
- `ChannelHandlerContext` 通信管道的上下文，用于传输业务数据
- `ChannelPipeline` 用于保存处理过程中需要用到的 `ChannelHandler` 和 `ChannelHandlerContext`
  - 存放 `ChannelHandler` 的容器
  - 可理解为用于组装链

#### Channel - 数据交互流程
- 事件传递给 `ChannelPipeline` 的第一个 `ChannelHandler`
- `Handler` 通过关联的 `Context` 传递事件给 `Pipeline` 中下一个 `Handler`

#### Channel 生命周期
- `channelUnregistered` channel 创建但未注册到 EventLoop
- `channelRegistered` channel 注册到 EventLoop
- `channelActive` channel 活动(连接到了 remote peer（远程对等方）)，可以接收和发送数据
- `channelInactive` channel 没有连接到 remote peer（远程对等方）

#### ChannelHandler 生命周期
- `handlerAdded` 当 ChannelHandler 添加到 ChannelPipeline 调用
- `handlerRemoved` 当 ChannelHandler 从 ChannelPipeline 移除时调用
- `exceptionCaught` 当 ChannelPipeline 执行发生错误时调用

##### 释放资源
- `SimpleChannelInboundHandler` 自动实现
- 其他实现 `ChannelInboundHandler`，要在 `channelRead()` 方法中
  - 通过 `ReferenceCountUtil.release(msg)` 释放
  - 或者用 `ctx.fireChannelRead(msg)` 传递到下个进行释放
- 出站时，除了释放外，还要通过 `promise.setSuccess()` 进行通知
- 泄漏检测工具类：`ResourceLeakDetector`

#### ChannelPipeline 
- 每一个新的 Channel，分配一个新的 ChannelPipeline
- 这个关联是永久性的
  - Channel 既不能附上另一个 ChannelPipeline，也不能分离当前这个

### ByteBuf
- 存储字节的容器，特点是使用方便
  - 有读索引和写索引，方便对整段字节缓存进行读写
- 可用 `Unpooled.copiedBuffer` 等创建
- 使用引用计数判断何时释放
- 利用池提高性能和降低内存消耗

#### ByteBuf 索引
- 有两个索引(读和写索引)，可同时进行读和写
- 写入索引和读取索引相同时，变为不可读
- "read"、"write" 方法都会提升相应索引
- "set"、"get" 方法不会移动索引位置
- 符合 `0 <= readerIndex <= writerIndex <= capacity - 1`

#### 三种使用模式
- Heap Buffer 堆缓冲区
  - 是 ByteBuf 最常用的模式，其将数据存储在堆空间
- Direct Buffer 直接缓冲区
  - 直接堆外内存，有两个好处：
  - - 避免中间交换的内存拷贝, 提升 IO 处理速度
  - - 避免频繁 GC 对应用线程的中断影响 
  - 内存空间的分配和释放上比堆缓冲区更复杂
- Composite Buffer 复合缓冲区
  - 相当于组合多个不同 ByteBuf
  - 避免组装消息时重新分配新的缓冲区

#### 方法说明
- `getMedium()` 返回当前索引的 24-bit 中间值
- `duplicate()`、`slice()` 创建一个展示内容的“视图”
- `copy()` 创建一个全新的有数据的副本

#### ByteBufHolder
- 对数据的一种封装
- `content()` 返回数据
- `copy()` 深复制，不共享数据，数据也是拷贝

#### ByteBufAllocator
- 用于分配 ByteBuf
- 提供池化和非池化

#### Unpooled
- 非池化缓存工具类
- 提供静态方法创建非池化的 ByteBuf 实例

#### ByteBufUtil
- 静态方法操作 ByteBuf，与使用池无关
- 最有价值的方法是 `hexDump()`，返回可读字节的十六进制字符串

### ChannelFuture
- 允许多个监听者 `ChannelFutureListener` 实例
- `Channel` 所有操作都会返回
- 实现“自底向上的异步和事件驱动”

### ServerBootstrap
- 配置服务器的启动代码
- 设置服务器绑定的端口
- 配置 Channel 的 Handler 实例
- 方法 `bind()` 启动服务
- 有两个事件组，一个负责创建 Channel，一个负责处理 Channel

### Bootstrap
- 初始化客户端
- 方法 `connect()` 连接远程服务器 
- 只有一个事件组

### EventLoop 
- 用于处理 Channel 的 I/O 操作
- 可以处理多个 Channel 事件
- 相当于一个线程，所有 Channel 的 I/O 始终用相同的线程来执行
  - 应用程序不需要同步 Netty 的 I/O 操作
- 一个 `EventLoopGroup` 可以有多个 EventLoop 
  - 并提供迭代用于检索

### 两种写消息方式
- 直接写消息给 Channel 
  - 消息从 ChannelPipeline 的尾部开始
- 或写入 ChannelHandlerContext
  - 消息从 ChannelPipeline 下一个处理器开始

----
---
### Codec
- 编码 / 解码器
- 完成字节与 POJO 的相互转换，达到自定义协议的目的
- 最有名的就是 `HttpRequestDecoder` 和 `HttpResponseEncoder`

#### Decoder 和 Encoder
- 分别是 `ChannelInboundHandler` 和 `ChannelOutboundHandler`
- 用于在数据流进来时将字节码转换为消息对象
  - 和数据流出去时将消息对象转换为字节码

##### Encoder
- 最重要的实现类是 `MessageToByteEncoder<T>`
- 作用是将消息实体 T 从对象转换成 byte，写入到 `ByteBuf`
  - 然后再传给后面的 `ChannelOutboundHandler` 到客户端

##### Decoder
- 参考实现类是 `ByteToMessageDecoder` 
- 服务端收到数据时，将字节流转换为实体对象
- 数据传到服务端可能不是一次请求就能完成的
  - 中间可能需要经过几次数据传输
  - 并且每一次传输传多少数据也是不确定的
  - 所以它有两个重要方法
- `decode` 和 `decodeLast` 调用时机不同
  - `decodeLast` 在 Channel 生命结束前调用，默认调用 `decode` 方法

#### ReplayingDecoder
- 用于数据长度更长，且不固定
- 无需检查缓冲区的数据是否足够
  - 若 `ByteBuf` 足够，则正常读取；否则停止解码
- 是一个有状态的 Handler，状态表示它目前读取到哪一步
  - `checkpoint(S s)` 设置当前状态，`state()` 获取当前状态

#### MessageToMessage
- 处理复杂的业务逻辑，完成 Message 和 Message 的相互转换
- HTTP 协议的处理，就用到很多 MessageToMessage 的派生类

#### CombinedChannelDuplexHandler
- 组合解码器和编码器，即：输入和输出
- 提高便利性

----
---
### HttpServer
#### HTTP 请求
- `HttpRequest` 请求头信息
- `HttpContent` 请求数据，后续可能有多个 `HttpContent` 部分
- `LastHttpContent` 标记 HTTP 请求结束，同时可能包含头的尾部信息
- 完整的 HTTP 请求（`FullHttpRequest`），由上面 3 部分组成

#### HTTP 响应
- `HttpResponse` 响应头信息
- `HttpContent` 响应数据，后续可能有多个 `HttpContent` 部分
- `LastHttpContent` 标记 HTTP 响应结束，同时可能包含头的尾部信息
- 完整的 HTTP 响应（`FullHttpResponse`），由上面 3 部分组成

#### HTTP 请求不是一次对话完成
- 中间可能有很多次连接
- 每一次对话都会建立一个 channel，一个 ChannelInboundHandler 不会同时去处理多个 channel
- 如何在一个 channel 里面处理一次完整的 HTTP 请求？
  - 用 FullHttpRequest，在处理 channel 时，处理消息是 FullHttpRequest 的 channel
  - 这样就能在一个 ChannelHandler 中处理一个完整的 HTTP 请求

#### HTTP ChannelHandler
- `HttpRequestEncoder`，客户端-用于编码 request
- `HttpRequestDecoder`，服务端-用于解码 request
- `HttpResponseEncoder`，服务端-用于编码 response
- `HttpResponseDecoder`，客户端-用于解码 response
- 可参考：`HttpClientCodec`、`HttpServerCodec`，一次解决

#### HttpObjectAggregator
- 消息聚合器，组装 `FullHttpRequest`、`FullHttpResponse`
  - 聚合的消息内容长度不能超过指定参数
  - 注释掉并将自定义的泛型去掉测试时，会出现多个 channel 处理
- 自定义的在最后

#### HTTP 压缩/解压
- 客户端 `HttpContentDecompressor`
- 服务端 `HttpContentCompressor`

#### 自定义 HTTP ChannelHandler
- 追加到最后
- 声明泛型为 `<FullHttpRequest>`
- 返回类型为 `FullHttpResponse`，避免拆分成多个 channel 返回
- 设置 HTTP 头 `content-length`，否则前端会一直刷新
- 最后调用 `ctx.flush()` 刷完，否则前端同样会一直刷新

----
---
### 定时调度
#### 自动关闭没有心跳的连接
- 利用 `ScheduledFuture`
- 可通过 `ChannelHandlerContext.executor().schedule(call, delay, unit)` 创建
- 支持延时提交，和取消任务 `Future.cancel(true)`

#### 空闲连接以及超时
- `IdleStateHandler` 连接闲置时间过长，触发 `IdleStateEvent` 事件
- `ReadTimeoutHandler` 在指定的时间内没收到入站数据则抛出 `ReadTimeoutException` 并关闭 Channel
- `WriteTimeoutHandler`