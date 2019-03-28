# 测试 Glowroot

## web
- 测试地址：localhost:9090
- Glowroot地址：localhost:4000

## JVM 参数
- `-javaagent:K:\install\plugin\APM\Glowroot\glowroot.jar`
- 如果启动时提示端口已绑定，则修改 `$Glowroot_home/admin.json` 的 `web/port`