## 参考
- https://blog.csdn.net/qq_38719039/article/details/78967632

## 导入
- 进入目录 `cd /d K:\install\jdk\jdk-11.0.1\lib\security`
- 导入 `keytool -cacerts -import -alias aa -storepass changeit -file C:\Users\Administrator\Desktop\aa\aa.cer`
  - 密码使用默认密码 `changeit` 就行了
- 查看 `keytool -cacerts -list -storepass changeit -alias aa`

### 更新：先删除原证书，再导入新的
- `keytool -cacerts -delete -alias aa -storepass changeit`
- `keytool -cacerts -import -alias aa -storepass changeit -file C:\Users\Administrator\Desktop\aa\aa.cer`
