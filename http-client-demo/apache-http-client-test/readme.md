# Apache HttpClient 测试

#### 连接池管理
- http://ifeve.com/http-connection-pool/
- 不能调用`response.close()`，因为其不是**归还**连接到连接池，而是**关闭连接**
- 使用自带工具类`EntityUtils.toString()`解析返回值
    - 内部读取流结束后，会**自动归还**连接到连接池
- 如果连接得不到**归还**，则首先会把连接池**打满**
    - 然后新来的请求从连接池拿不到连接
    - 会抛出`ConnectionPoolTimeoutException`异常
    - 使用 `requestConfig.setConnectionRequestTimeout(timeout)`
        - 设置获取连接池的连接的超时时间
- `HttpRoute` 默认将 `https` 设为 `secure`
    - `new` 的时候要指定下，要不然 `max` 连接数设置无效
- 连接池要有效，`ConnectionManager` 只能是一个实例