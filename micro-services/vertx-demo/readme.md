# vert.x demo

## vertx-simple
简单 demo

## vertx-web
web 服务 demo

## vertx-web-mongodb
web 服务 和 mongodb demo

## 原理
- 可以创建多个事件循环，每个 CPU 核心一个
- `Verticle` 是基本组件，和一个事件循环关联
- HTTP 请求时，vert.x 会封装成事件，给它处理
- `Verticle` 之间独立，不共享内存
  - 用 Event Bus（事件总线）方式，通过消息传递进行通信
  - 因此可以实现真正的分布式