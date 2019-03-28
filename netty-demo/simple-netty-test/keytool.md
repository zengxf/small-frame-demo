# 使用 `keytool` 生成证书

- [原文参考](https://blog.csdn.net/u011350541/article/details/71941536)

## 为服务器生成证书
### 命令格式
```
keytool 
-genkey 
-alias tomcat (别名) 
-keypass 123456 (别名密码) 
-keyalg RSA (算法) 
-keysize 1024 (密钥长度) 
-validity 365 (有效期，天单位) 
-keystore D:/keys/tomcat.keystore (指定生成证书的位置和证书名称) 
-storepass 123456 (获取 keystore 信息的密码)
```

#### 生成命令
```
keytool -genkey -alias zxf-key-server -keypass 123456 -keyalg RSA -keysize 1024 -validity 365 -keystore my-https.keystore -storepass 123456 ^
-dname "C=cn,ST=gd,L=gz,O=ht,OU=org,CN=localhost" 

## 根据提示输入相关信息，如：
您的名字与姓氏是什么?
  zxf
您的组织单位名称是什么?
  ht
您的组织名称是什么?
  org
您所在的城市或区域名称是什么?
  gz
您所在的省/市/自治区名称是什么?
  gd
该单位的双字母国家/地区代码是什么?
  cn
CN=zxf, OU=ht, O=org, L=gz, ST=gd, C=cn是否正确?
  y
```

## 为客户端生成证书
- 为浏览器生成证书，以便让服务器来验证它
- 为了能将证书顺利导入至浏览器，证书格式应该是 PKCS12

### 生成命令
```
keytool -genkey -alias zxf-key-client -keypass 123456 -keyalg RSA -keysize 1024 -validity 365 -storetype PKCS12 -keystore client.p12 -storepass 123456 ^
-dname "C=cn,ST=gd,L=gz,O=ht,OU=org,CN=localhost" 
```

## 让服务器信任客户端证书
### 把客户端证书导出为一个单独的 CER 文件
```
keytool -export -alias zxf-key-client -keystore client.p12 -storetype PKCS12 -keypass 123456 -file client.cer

## 注意：
- keypass：指定 CER 文件的密码，但会被忽略，而要求重新输入
```

### 将 CER 导入到服务器的证书库，添加为一个信任证书
```
keytool -import -v -file client.cer -keystore my-https.keystore -storepass 123456

## 完成之后通过 list 命令查看服务器的证书库
-  可以看到两个证书，一个是服务器证书，一个是受信任的客户端证书
keytool -list -v -keystore my-https.keystore
```

## 让客户端信任服务器证书
### 先把服务器证书导出为一个单独的 CER 文件
```
keytool -keystore my-https.keystore -export -alias zxf-key-server -file my-https.cer

## 双击 my-https.cer 文件，按照提示安装证书
-  将证书填入到“受信任的根证书颁发机构”
- 或直接填入到浏览器根证书

## 服务端就用 client.cer
```