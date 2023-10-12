package cn.zxf.test;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.List;

@Slf4j
@AllArgsConstructor
public abstract class AbstractMain extends Application {

    protected final String fxmlPath;

    @Override
    public void start(Stage primaryStage) {
        try {
            URL url = AbstractMain.class.getResource(this.fxmlPath);
            Parent root = FXMLLoader.load(url);
            primaryStage.setTitle("JavaFX 测试");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}