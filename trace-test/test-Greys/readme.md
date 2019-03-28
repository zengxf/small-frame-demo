## 网站
- [参考](https://github.com/oldmanpushcart/greys-anatomy/wiki)

### 安装
```
./install-local.sh
```
### 连接
```
./greys.sh <pid>
```
### 内部命令
#### help
```
help
help trace # 查看子命令
```
#### trace
```
trace -n 2 *TestTrace* test '#cost>900'
-----------
-n 2 ：代表只打印2次就退出（防止刷屏，影响性能）；
*TestTrace ：监听的类名
test : 监听的方法名
'#cost>1000' : 打印条件为耗时超过 1000ms
------------------------------------------------
执行后，会显示：
Press Ctrl+D to abort.
Affect(class-cnt:1 , method-cnt:2) cost in 120 ms.
-----------
代表动态修改了一个类，对一个方法（例如方法重载）进行监控，修改花费120毫秒。
------------------------------------------------
如果出现满足条件的情况，则我们会看到打印结果：
`---+Tracing for : thread_name="test-1" thread_id=0x9;is_daemon=false;priority=5;
    `---+[1377,1377ms]cn.zxf.greys.test.TestTraceThread:test()
        +---[1,0ms]java.util.Random:<init>(@34)
        +---[1,0ms]java.util.Random:nextInt(@34)
        +---[571,570ms]java.lang.Thread:sleep(@34)
        +---[571,0ms]java.util.Random:<init>(@35)
        +---[571,0ms]java.util.Random:nextInt(@35)
        +---[1350,779ms]cn.zxf.greys.test.TestTraceThread:test1(@36)
        `---[1377,27ms]cn.zxf.greys.test.TestTraceThread:test12(@39)
-----------
可以看到，主要耗时在
        +---[1350,779ms]cn.zxf.greys.test.TestTraceThread:test1(@36)
	[1350,779ms]
	第一个元素：总耗时，栈的相对时间戳
	第二个元素：方法的实际耗时
```
#### reset 
恢复增强类（即被动态修改的代码），用于退出前
#### shutdown 
关闭greys 并退出
