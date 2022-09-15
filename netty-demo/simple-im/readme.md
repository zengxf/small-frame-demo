# 快速创建 Gradle 多模块（Spring Web）项目-脚手架

## 插件设置问题
- 参考： https://stackoverflow.com/questions/26236308/how-to-apply-plugin-to-allprojects-with-new-gradle-plugins-mechanism

## Gradle 相关
- 查看所有项目：`gradle -q projects`
- 构建所有项目：`gradle build -x test`
- **清理**单个项目：`gradle :b-server:clean`
- **构建**单个项目：`gradle :b-serverb-server:build -x test`