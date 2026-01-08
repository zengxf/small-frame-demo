# ProtoBuf 测试

## 问题
- proto 编码报错 IDEA 设置：https://www.cnblogs.com/wxw310415/p/13553727.html
- 报错没关系，可以用命名直接生成


## 下载 protoc
- https://github.com/protocolbuffers/protobuf/releases

- 对应 `3.21.x` 版本
  - https://github.com/protocolbuffers/protobuf/releases/tag/v21.2
  - 选 `protoc-21.2-win64.zip`
    - 最终：https://github.com/protocolbuffers/protobuf/releases/download/v21.2/protoc-21.2-win64.zip
  - 解压 & 添加到 Path

- 下面的太新了，对应的是 `4.x` 版本 
  - https://github.com/protocolbuffers/protobuf/releases/tag/v33.0
  - 选 `protoc-33.0-win64.zip`
    - 最终：https://github.com/protocolbuffers/protobuf/releases/download/v33.0/protoc-33.0-win64.zip
  - 解压 & 添加到 Path

- `3.14.x` 版本
  - https://github.com/protocolbuffers/protobuf/releases/tag/v3.14.0
  - 旧的项目使用，如果用新的 (如 `3.21.x`) 生成，编译没问题，转数组会报错


### 命令
```shell
# 查看版本
protoc --version

# 查看帮助
protoc -h
```