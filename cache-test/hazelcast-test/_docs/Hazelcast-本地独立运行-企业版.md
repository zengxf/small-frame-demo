# Hazelcast-本地独立运行-企业版

## 版本

- 5.6.0 (2026 年年初的最新稳定版本)

## 下载

- https://hazelcast.com/get-started
- 输入相关信息后会收到邮件，从邮件中点击链接
- 下载 slim 就行

## 改配置

- 改 `config/hazelcast.xml`
- 添加 `<license-key>` 标签

```xml
<?xml version="1.0" encoding="UTF-8"?>

<hazelcast xmlns="http://www.hazelcast.com/schema/config"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.hazelcast.com/schema/config
           http://www.hazelcast.com/schema/config/hazelcast-config-5.6.xsd"
>
    <!-- 在这里添加您的授权码 -->
    <license-key>邮件收到的Key</license-key>

    <!-- 设置集群名 -->
    <cluster-name>zxf_dev</cluster-name>

    <!-- CP 配置 -->
    <cp-subsystem>
        <cp-member-count>3</cp-member-count>
        <group-size>3</group-size>
    </cp-subsystem>
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

- 也可直接在 `common.bat` 加

```bash
set JAVA_HOME=D:\Install\Java\JDK\jdk-25
```

## 运行

```bash
# 用 cmd 运行 (用上面的 JDK 版本)

# 进入 hazelcast-enterprise-5.6.0-slim 中运行
cd bin

# 启动 (可重复运行 3 次，启动 3 个实例)
hz-start
```