package cn.zxf.test.echarts;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p/>
 * 曾献峰(656553) 创建于 2023/10/12
 */
@Slf4j
@AllArgsConstructor
public abstract class BaseEChartTest extends Application {

    protected final String htmlPath;

    @Override
    public void start(Stage stage) {
        log.info("启动：[{}]", htmlPath);
        long start = System.currentTimeMillis();
        
        stage.setTitle("Web View Sample");
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(this.getClass().getResource(htmlPath).toExternalForm());
        Scene scene = new Scene(webView, 900, 600, Color.web("lightgray"));
        stage.setScene(scene);
        webEngine.getLoadWorker()
                .stateProperty()
                .addListener((ObservableValue<? extends State> ov, State oldState, State newState) -> {
                    if (newState == State.SUCCEEDED) {
                        stage.show();
                        log.info("use-time-2: [{}]ms", System.currentTimeMillis() - start);
                    }
                });

        log.info("use-time-1: [{}]ms", System.currentTimeMillis() - start);
    }

}
