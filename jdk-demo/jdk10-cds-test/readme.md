# CDS 测试
- 类数据共享（Class Data Sharing）
- [原文参考](https://www.jianshu.com/p/890196bf529a)
- 测试参考 `TestMain` 类

## 测试
```
// 测试要用 jar 包运行，要不然会有 `Error: non-empty directory 'xx\bin\main'` 错误包
// 测试 1 - 不共享&导出列表：
-Xshare:off -XX:+UseAppCDS -XX:DumpLoadedClassList=out/hello.txt
// 测试 2 - 共享&导出类数据：
-Xshare:dump -XX:+UseAppCDS -XX:SharedClassListFile=out/hello.txt -XX:SharedArchiveFile=out/hello.jsa
// 测试 3 - 使用共享类数据：
-Xshare:on -XX:+UseAppCDS -XX:SharedArchiveFile=out/hello.jsa
// 测试-打印详细日志： 
-Xlog:class+path=info 或 -Xlog:class+load=info
```