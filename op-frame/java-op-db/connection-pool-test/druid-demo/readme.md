# druid demo
- 提供强大的监控和扩展功能
- 对攻击 SQL，进行拦截，防御SQL注入攻击
- 连接泄漏监测，申请的连接忘记关闭，def: 30min
- 没有类似ExceptionSorter的连接池，数据库重启或者网络中断之后，不能恢复工作——支持的
- 锁的公平模式可以配置
- 慢SQL日志记录
