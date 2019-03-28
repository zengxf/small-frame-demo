# vertx-web docker 测试

## 构建 Jar
- `gradle build -x test`

## 构建容器
- `docker build --no-cache -t vertx-web:latest .`

### 运行
```
docker run -p 9090:8080 -e SERVER_PORT=8080 --name vertx-web -d vertx-web:latest
docker run -p 9091:8081 -e SERVER_PORT=8081 --name vertx-web-bck -d vertx-web:latest

# 一次性测试
docker run -p 9090:8080 -e SERVER_PORT=8080 --rm -d vertx-web:latest

# 查看
docker ps
```

### 滚动发布
```
# 先打备份 tag
docker tag vertx-web:latest vertx-web:previous
# 重新构建镜像
docker build --no-cache -t vertx-web:latest .

# 删除主容器
docker rm -f vertx-web
# 运行主容器
docker run -p 9090:8080 -e SERVER_PORT=8080 --name vertx-web -d vertx-web:latest

# 删除备份容器
docker rm -f vertx-web-bck
# 运行备份容器
docker run -p 9091:8081 -e SERVER_PORT=8081 --name vertx-web-bck -d vertx-web:latest
```

### 清空 none 镜像
- `docker image prune`