# JavaFX 17 测试
- 直接用 `SceneBuilder 17` 就好了

## 初始化 & 运行
- IDEA 可直接运行

## 创建镜像
- `gradle jlink`
- 位置在：`build\image\bin\simple-test-fx17`

## 问题
### 运行创建的镜像出错
- 将当前模块开放给 JavaFX

### idea 运行 main 找不到资源
- 配置 `gradle [:run]` 运行
- 后面用 `idea` 插件解决了，可直接运行 `Main`

### idea 运行
- 点击 Gradle 图标，配置输入 `:run`
  - 即可运行和调试
- 也可直接：`Tasks` → `application` → `run` 运行

## 参考
- 直接看官网和易百教程

## 源码说明
### MyController.java
- 可以不用注入属性 `showDateButton`
- 可以不用实现接口 `Initializable`
- `Initializable` 的 URL 是引用的 `fxml` 文件路径

