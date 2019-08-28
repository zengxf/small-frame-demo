# JavaFX 简单测试
- 没必要用 Eclipse 插件开发
- 直接用 `JavaFX Scene Builder 2.0` 就好了

## 初始化 & 运行
- `gradle init --type java-library`
- `gradlew run` 
  - 需要 **gradlew** 运行
  - 和 `gradle/wrapper/gradle-wrapper.jar` 配合
- 也可直接 `gradle run` 运行，不需要上面两步
- 直接用 Eclipse 运行会报错：`错误: 缺少 JavaFX 运行时组件, 需要使用该组件来运行此应用程序`

## 引用 jar 包
```
javafx {
    version = "11.0.2"
    modules = [ 'javafx.controls', 'javafx.fxml' ]
}
```

## 创建镜像
- `gradlew jlink`
- 位置在：`build\image\bin\simple-test-fx`

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

