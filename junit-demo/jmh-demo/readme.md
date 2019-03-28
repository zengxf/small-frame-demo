# JMH 微基准测试
- 将 java-home 设为 java8

## logging 测试
### 生成项目
mvn archetype:generate -DinteractiveMode=false \
      -DarchetypeGroupId=org.openjdk.jmh -DarchetypeArtifactId=jmh-java-benchmark-archetype -DarchetypeVersion=1.19 \
      -DgroupId=cn.zxf.jmh -DartifactId=logging-demo -Dversion=1.0
### 安装
mvn clean		// 清理
mvn compile	// 编译
mvn install	// 打包
### 测试
java -jar target/benchmarks.jar -wi 5 -i 4 -t 3 -f 1


## 使用 gradle 的项目测试
gradlew jhw