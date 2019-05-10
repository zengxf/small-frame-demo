# JavaFX 简单测试
- 没必要用 Eclipse 插件开发
- 直接用 `JavaFX Scene Builder 2.0` 就好了

## 初始化 & 运行
- `gradle init --type java-library`
- `gradlew run` 
  - 需要 **gradlew** 运行
  - 和 `gradle/wrapper/gradle-wrapper.jar` 配合
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

## 参考
- 直接看官网和易百教程

