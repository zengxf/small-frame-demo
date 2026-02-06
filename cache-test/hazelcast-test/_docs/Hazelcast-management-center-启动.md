## 下载

- **hazelcast-5.6.0-slim** 没有包含
    - 下载
    - https://hazelcast.com/community-edition-projects/downloads/
    - 看 **Hazelcast Management Center**
- **hazelcast-5.6.0** 完整版是包含的，在 `management-center` 目录下

---

## 启动

- 完整版，在 `management-center` 目录下
- 改 `bin\mc-start.cmd`，加

```bash
set JAVA_HOME=D:\Install\Java\JDK\jdk-25 
```

- 启动

```bash
mc-start
```

- 查看 http://localhost:8080/
- 选**开发模式**
- 添加连接
    - Member Addresses: `127.0.0.1:5701`
    - Cluster Name: `zxf_dev`
- **_貌似看不到 Map 具体的值，只是统计信息_** 