# Netty 基本功能测试
- 主要参考：疯狂创客圈 `netty_redis_zookeeper_source_code`
    - https://gitee.com/crazymaker/netty_redis_zookeeper_source_code

## 设置证书
```js
// 使用 PowerShell 执行，` 为换行
// 指定目录
cd D:\Data\test\key

// 第一步：生成服务端密钥
keytool -genkey -alias server -keypass 123456 -keyalg RSA -keysize 2048 `
  -validity 365 -keystore server.jks -storepass 123456 -dname "CN=server"
// 查看：输入密码 123456 `
keytool -list -v -keystore server.jks

// 第二步：生成客户端密钥
keytool -genkey -alias client -validity 365 -keyalg RSA -keysize 2048 `
  -dname "CN=client" -keypass 123456 -storepass 123456 -keystore client.jks
// 查看：输入密码 123456 `
keytool -list -v -keystore client.jks

// 第三步：将服务端证书导出
keytool -export -alias server -keystore server.jks -storepass 123456 `
  -file server.cer
// 查看 `
keytool -printcert -file server.cer
keytool -export -alias client -keystore client.jks -storepass 123456 `
  -file client.cer

// 第四步：将服务端证书导入到客户端仓库（即信任仓库，可以和密钥仓库合用）`
keytool -import -trustcacerts -alias server -file server.cer -keystore `
  client.jks -storepass 123456
// 导入会提示：是否信任该证书？输入 y 即可 `
keytool -import -trustcacerts -alias client -file client.cer -keystore `
  server.jks -storepass 123456

// 其他：`
//   根据别名从密钥存储仓库文件中删除一个证书或者密钥
keytool -delete -keystore client.jks -alias server
```