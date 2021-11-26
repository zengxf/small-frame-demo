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

    static List<String> cssList = List.of(
            "css/darcula.css",              // 太灰暗
            "css/dark-theme.css",           // 刚好暗，按钮大小改了
            "css/waitomo.css",              // 刚好暗，按钮没变
            "css/modena_dark.css",          // 暗
            "css/NewThemeCssFluent.css",    // 灰
            "css/win7glass.css",            // Win7 风格
            "css/brume.css",                // 无效
            ""
    );
    static ObservableList<String> styles;
    static int index;

    protected final String fxmlPath;


    @Override
    public void start(Stage primaryStage) {
        try {
            URL url = AbstractMain.class.getResource(this.fxmlPath);
            Parent root = FXMLLoader.load(url);
            primaryStage.setTitle("JavaFX 测试");
            Scene scene = new Scene(root);
            styles = scene.getStylesheets();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changeStyle() {
        styles.clear();
        int len = cssList.size();
        int cssIndex = index % len;
        String css = cssList.get(cssIndex);
        log.info("改样式：[{}: {}]", cssIndex, css);
        if (!css.isEmpty())
            styles.add(css);
        index++;
    }

}