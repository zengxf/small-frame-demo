## Feign 测试
### 总结
- 默认不打印日志，要启用需加代码设置：
  - `.logger(new Slf4jLogger()).logLevel(Logger.Level.FULL)`
- 使用 **Jackson**，已处理空指针校验问题
- **POST** 请求，要手动声明 `json` 头