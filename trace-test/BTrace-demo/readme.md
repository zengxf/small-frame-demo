# BTrace 测试

## [下载](https://github.com/btraceio/btrace)
- 下载 `btrace-bin-<version>.zip` 文件

## 脚本注意事项: 
- 1. 只能调用：`BTraceUtils.*` 的方法
- 2. 还不支持 Java9+

### 要调用其他类的方法
- 需显示设置 `@BTrace( unsafe = true )`
- 其他可能需要改 `btrace.bat` 里面的 `-Dcom.sun.btrace.unsafe` 为 `true`

## 执行
```
# 到 *Script.java 目录下执行：
jps
btrace <pid> XXScript.java
btrace -cp build <pid> XXScript.java
```

---
## BTraceUtils 方法

### println(String)
- 中文会乱码

### jstack()
- 打印调用栈


---
## @OnMethod clazz 属性支持
```
+xx.Class		// 继承或实现
@xx.Annotation	// 有某注解
```

## 其他
- **btracer** 脚本用着不爽 