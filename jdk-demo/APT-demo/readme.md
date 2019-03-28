## APT-demo
- APT 简单测试

### javac 命令
- `..\src\main\java> javac -processorpath ../../../lib/APT-demo.jar cn/zxf/apt/demo/RouteService.java`

### gradle 命令
- `gradle build`（先注释掉：`//apt files('lib/APT-demo.jar')`）
- `gradle copyJar`
- `gradle build`（再打开：`apt files('lib/APT-demo.jar')`）
- 在 `build\generated` 下会生成相应 `XX.java` 文件
- 并且编译后在 `build\classes` 下会有相应的 `XX.class` 文件
- 打包后的 `xx.jar` 也会有相应的类