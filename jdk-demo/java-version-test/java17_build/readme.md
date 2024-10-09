# Java 17 打包测试

## JLink 测试
```shell
# Git Bash 下运行

# 这种麻烦 (且没试成功)
jlink \
  --module-path ./build/classes/java/main/ \
  --add-modules java.base,java.xml \
  --output D:\Data\Test\test \
  --ignore-signing-information \
  --launcher app=cn.test/cn.test.TestMain

# 使用 Gradle 插件
gradle jlink

# 运行
# GitBash
./build/image/bin/java17-build
# cmd
"build/image/bin/java17-build.bat"
```

### 原理
- 将依赖的 jar 包和代码打包到 `./build/image/lib/modules` 里面

### 解压
```shell
# 进目录
cd ./build/image/lib

# 提取
jimage extract modules 
```