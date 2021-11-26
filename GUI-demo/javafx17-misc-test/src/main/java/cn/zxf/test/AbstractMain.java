package cn.zxf.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;

import java.net.URL;

@AllArgsConstructor
public abstract class AbstractMain extends Application {

    static String
            CSS1 = "css/darcula.css",       // 太暗
            CSS2 = "css/dark-theme.css",    // 不暗，按钮大小改了
            CSS3 = "css/waitomo.css";       // 不暗，按钮没变

    protected final String fxmlPath;

    @Override
    public void start(Stage primaryStage) {
        try {
            URL url = AbstractMain.class.getResource(this.fxmlPath);
            Parent root = FXMLLoader.load(url);
            primaryStage.setTitle("JavaFX 测试");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(CSS3);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}