## 参考
- https://www.graalvm.org/latest/reference-manual/native-image/#gradle
- Spring-Boot 参考 https://springdoc.cn/spring-boot/native-image.html
```shell
# 初始化项目
gradle init --project-name helloworld --type java-application --test-framework junit-jupiter --dsl groovy

# 编译
gradle nativeCompile

# 运行 (传参测试)
./build/native/nativeCompile/JDK17-test  ab cd  -Dtest.sign=233
./build/native/nativeCompile/JDK17-test  -Dtest.sign=688  ef gh
```

## 问题
1. `This probably means that JDK at isn't a GraalVM distribution.`
```shell
# 问题：
Execution failed for task ':nativeCompile'.
> Determining GraalVM installation failed with message: 'gu.cmd' at 'D:\Install\Java\JDK\jdk-17\bin\gu.cmd' tool wasn't found. ...

# 解决:
将 graalvm-jdk 设置到环境中，替换 openjdk
```

2. `Please specify class (or <module>/<mainclass>) ...`
```shell
# 问题：
Error: Please specify class (or <module>/<mainclass>) containing the main entry point method. (see --help)
FAILURE: Build failed with an exception.

# 解决：
在 build.gradle 设置 application 插件
并设置主类 mainClass = 'test.TestMain'
```

3. `Failed to find 'vcvarsall.bat'`
```shell
# 问题：
Error: Failed to find 'vcvarsall.bat' in a Visual Studio installation.
Please make sure that Visual Studio 2022 version 17.1.0 or ... . You can download it at https://visualstudio.microsoft.com/downloads/. ...

# 解决：
参考安装 https://www.graalvm.org/jdk17/docs/getting-started/windows/
参考配置 https://stackoverflow.com/questions/77840039/graalvm-failed-to-find-vcvarsall-bat-in-a-visual-studio-installation
```