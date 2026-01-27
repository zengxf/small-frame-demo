# Hazelcast-本地独立运行-社区版

## 版本

- 5.6.0 (2026 年年初的最新稳定版本)

## 下载

- https://hazelcast.com/community-edition-projects/downloads/
    - 下载 slim 就行
- 也可在 https://hazelcast.com/get-started/ 搜索 `Community Edition Downloads` 链接，点击进入

## 改配置

- 改 `config/hazelcast.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>

<hazelcast xmlns="http://www.hazelcast.com/schema/config"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.hazelcast.com/schema/config
           http://www.hazelcast.com/schema/config/hazelcast-config-5.6.xsd"
>
    <!-- 设置集群名 -->
    <cluster-name>zxf_dev</cluster-name>
</hazelcast>
```

## 改 JDK 版本

```bash
# 用 cmd 运行

# JDK 版本
set JAVA_HOME=D:\Install\Java\JDK\jdk-25
set PATH=%JAVA_HOME%\bin;%PATH%
java -version
```

## 运行

```bash
# 用 cmd 运行 (用上面的 JDK 版本)

# 进入 hazelcast-5.6.0-slim 中运行
cd bin

# 启动
hz-start
```