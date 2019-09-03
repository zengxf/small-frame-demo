# JavaFX 复杂测试

## 经验
### 面板隐藏
```jshelllanguage
//    @FXML
//    private Pane pane;
@FXML
private Parent pane;

//    Stage stage = (Stage) pane.getScene().getWindow();
//    stage.close(); // 实际也是调用 hide() 方法
pane.getScene().getWindow().hide();
```

### 消息弹窗
- [原文参考](https://blog.csdn.net/qq_26954773/article/details/78215554)
```jshelllanguage
Alert alert = new Alert(Alert.AlertType.WARNING);
alert.setTitle("消息提示");
alert.setHeaderText("登录出错");
alert.setContentText("用户不存在或密码错误");
alert.showAndWait();
```