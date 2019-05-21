# MyBatis demo 
- [原理参考](https://blog.csdn.net/u010349169/column/info/mybatis-principle)
- [官方文档](http://www.mybatis.org/mybatis-3/zh/getting-started.html)

## 源码分析
### 配置类 `Configuration`
- environment：环境属性
  - 其属性有：事务工厂、数据源（连接池）
- mapperRegistry：mapper 注册
- 整个配置的其他属性

### 数据源（连接池） `DataSourceFactory`
- UnPooled 不使用连接池的数据源
- Pooled 使用连接池的数据源
- JNDI 使用 JNDI 实现的数据源

### 事务管理机制，类 `Transaction`
- JDBC：直接用 Connection 对象进行提交、回滚、关闭
- Managed：MyBatis 自身不管理，让程序的容器（如 JBoss、Spring）管理

### 执行流程
- `SqlSession` -> `Executor` -> `StatementHandler`

### 缓存
#### 一级缓存
- SqlSession 级别的简单缓存
  - 创建时会相应初始化，关闭时会清空，`clearCache()` 可显示清空会
- 参考 `BaseExecutor`，当执行 update（增删改）时，也会清空缓存
- 问题：大量查询时，会缓存不释放，导致 OOM

#### 二级缓存
- Application(应用)级别的缓存，相当于全局缓存
- 启用：`cacheEnabled=true` 和 `useCache="true"` 和 `<cache> | <cached-ref>`
- 参考 `CachingExecutor` 类
  - 相当于 `Executor` 装饰者
  - 查询时先看自己的缓存，再调用真正的 `Executor`
  - 当执行 update 时，也会判断清空缓存
- 可为每个 `Mapper` 设置一个，也可多个 `Mapper` 设置一个
- 问题：1) 每个 `Mapper` 不同缓存时，Join 查询缓存后，其他表更新时，没有更改缓存
  - 2) 多个 `Mapper` 同一缓存时，各自更新操作时，会频繁清空缓存

### MyBatis 占位符
- `#{}` 是预定义
- `${}` 只是简单替换，会导致 SQL 注入