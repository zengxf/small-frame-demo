# JMH 微基准测试
- 将 java-home 设为 java8

## Maven 测试
### maven-jmh-test
#### 生成项目
```
mvn archetype:generate -DinteractiveMode=false \
      -DarchetypeGroupId=org.openjdk.jmh -DarchetypeArtifactId=jmh-java-benchmark-archetype -DarchetypeVersion=1.19 \
      -DgroupId=cn.zxf.jmh -DartifactId=logging-demo -Dversion=1.0
```

#### 安装
```
mvn clean		// 清理
mvn compile		// 编译
mvn install		// 打包
```

#### 测试
- `java -jar target/benchmarks.jar -wi 5 -i 4 -t 3 -f 1`


---
## Gradle 测试
- jmh 类要放在 `src/jmh/java` 下面
- [jmh 插件参考](https://github.com/melix/jmh-gradle-plugin)
- 方法里面最好不用输出，如：`System.out.println( "hello" );`

### gradle-jmh-test
- `gradle jmh`