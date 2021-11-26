# JavaFX 复杂测试

## 经验
### 面板可直接加载出来 
- `TabPane page = FXMLLoader.load(url);`

### 面板复杂加载
```jshelllanguage
URL url = MiscController.class.getResource("/test-1.fxml");
FXMLLoader loader = new FXMLLoader();
loader.setLocation(url);

TabPane page = loader.load();
Scene scene = new Scene(page);

Test1Controller test1 = loader.getController(); // 要先 load 才有 controller
test1.setValue(true);
``` 

## 问题
### 要先 load() 才有 controller
### 控制台乱码
- 添加 JVM 参数 `-Dfile.encoding=UTF-8`

### 使用 lombok 需要在 module-info.java 里面声明
### 嵌套 Controller
- 用 `<fx:include />` 嵌入
- 代码里面的命名格式：`$id + Controller`

### 其他
1. 泛型编译不过，要单独写出来
2. 设置编辑，首先 `Table` 要启用编辑，编辑完要按 `Entry` 键才会提交
3. 要使表格里面的复选框有效，也要启用表格编辑

## 代码备份
- Gradle
```Groovy
tasks.withType(JavaCompile) {
    options.encoding = "UTF-8"
}
```

### Java
```Java
// 这种方式写常量，不推荐
nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

// 启动。可省略，继承 Application 类就可以了
public static void main(String[] args) {
    launch(args);
}
```