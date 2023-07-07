## 官网

- https://helidon.io/

## 打包

```js
// 没有包含依赖包 
gradle build -x test

// 使用此命令才包含依赖包
gradle shadowJar

// 启动
java -jar build/libs/helidon-simple-v3-all.jar
```

- 启动 jar 的测试路径： http://localhost:8080/greet