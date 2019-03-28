## agent 项目说明
- [参考-项目示例](https://mp.weixin.qq.com/s?__biz=MzI0NzEyODIyOA==&mid=2247483860&idx=1&sn=5bf9cf25651f537d095bf6866e46f1ac) 
- [参考-IBM](https://www.ibm.com/developerworks/cn/java/j-lo-jse61/index.html)
- my-agent-01 简单打印
- my-agent-02 替换类
- my-agent-03 测试 Java SE 6 的 agentmain 

## 格式说明 
```
-javaagent:jarpath=[options] 
其中的 jarpath 为 agent.jar 的路径，
options 是一个可选参数，其值会被 premain 方法的第一个参数接收 public static void premain(String options, Instrumentation ins).
```

## 打 agent 包
```
gradle build
```

## 使用代理 JVM 参数
### 测试1
```
AgentTestMain
-javaagent:M:\project\zxf_super_demo\small-frame\jdk-demo\instrument-demo\my-agent-01\build\libs\my-agent-01.jar=hello
```
### 测试2 - 多个
```
AgentTestMain
-javaagent:M:\project\zxf_super_demo\small-frame\jdk-demo\instrument-demo\my-agent-01\build\libs\my-agent-01.jar=hello
-javaagent:M:\project\zxf_super_demo\small-frame\jdk-demo\instrument-demo\my-agent-01\build\libs\my-agent-01.jar=hello2
```
### 测试3 - 替换类
```
AgentTestMain
-javaagent:M:\project\zxf_super_demo\small-frame\jdk-demo\instrument-demo\my-agent-02\build\libs\my-agent-02.jar=hello
```
### 测试4 - Java SE 6 程序附加
```
1) 先启动 Jse6ThreadAttachTestMain
2) 跟着立即启动 LoopDogTest
```
- 测试结果是：能附加但没有进去改类的字节码

## 失败经验
### 读取 jar 包中的文件，用流不要用 URI
```
// Files.readAllBytes( Paths.get( GreetingTransformer.class.getResource( "/classes/Dog.class.file" ).toURI() ) ); // 读取出错，且不会打印异常
InputStream is = GreetingTransformer.class.getResource( "/classes/Dog.class.file" ).openStream();
byte[] bytearr = new byte[is.available()];
is.read( bytearr );
return bytearr;
```

## 知识解释
### Java SE 5
```
public static void premain(String agentArgs, Instrumentation inst);  [1]
public static void premain(String agentArgs); [2]
# [1] 的优先级比 [2] 高，将会被优先执行（[1] 和 [2] 同时存在时，[2] 被忽略）
```
### Java SE 6
#### 可以在 main 函数开始运行之后再运行
```
public static void agentmain (String agentArgs, Instrumentation inst);          [1] 
public static void agentmain (String agentArgs);            [2]
# [1] 的优先级比 [2] 高，将会被优先执行。
```

