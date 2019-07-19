## 网站
- [链接](https://alibaba.github.io/arthas/)
- 读音：`阿尔萨斯`

## 命令示例
- [参考网址](https://alibaba.github.io/arthas/advanced-use.html)

### 启动 `as` `as <pid>`
- `dashboard` 查看线程和内存使用信息
- `watch cn.zxf.arthas.test.TestWhileLoop$Counter value returnObj` 查看函数返回值
- `watch cn.zxf.arthas.test.TestWhileLoop$Counter value params` 查看函数参数
- `quit` `exit` 退出。`shutdown` 完全关闭
- `session` 查看当前会话信息
- `sc -df cn.zxf.arthas.test.TestWhileLoop` 查看已加载的类信息
- `sm -d cn.zxf.arthas.test.TestWhileLoop` 查看已加载类的方法信息
- `jad cn.zxf.arthas.test.TestWhileLoop` 反编译类
- `classloader` 查看 ClassLoader 的继承树，urls，类加载信息
- `monitor -c 5 cn.zxf.arthas.test.TestWhileLoop$Counter increment` 方法执行监控
- `trace cn.zxf.arthas.test.TestWhileLoop$Counter value` 监视方法调用路径和耗时
- `trace cn.zxf.arthas.test.TestWhileLoop$Counter value #cost>0.004` 按照耗时过滤
- `stack cn.zxf.arthas.test.TestWhileLoop$Counter value` 输出方法被调用的调用路径
- `stack cn.zxf.arthas.test.TestWhileLoop$Counter value #cost>0.004` 按照耗时过滤
- `thread -n 10` 将最忙碌的 10 个线程快照打印出来
- `tt -t -n 3 *TestWhileLoop$Counter value` 方法执行数据的时空隧道，记录方法的次调用情况
- `tt -l` 列出历史记录
- `tt -i 1001` 查看某一条记录

### Web 访问
- [一般地址](http://localhost:8563/)
