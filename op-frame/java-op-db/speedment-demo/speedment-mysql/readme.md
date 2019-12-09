# 流式 SQL 操作
- [参考](https://github.com/speedment/speedment/)

## 生成代码
```
mvn eclipse:eclipse -DdownloadSources=true
mvn speedment:tool
```

## 其他命令
```
mvn speedment:clean
mvn speedment:reload
mvn speedment:generate
```

## 其他 
- 还不支持 Gradle
- 隐藏的 .*.java.md5 文件是用作校验文件有没有更改，如果更改则不会重新生成