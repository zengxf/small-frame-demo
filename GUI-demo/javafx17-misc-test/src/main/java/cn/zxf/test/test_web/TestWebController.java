package cn.zxf.test.test_web;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.web.WebView;
import lombok.extern.slf4j.Slf4j;

/**
 * <br/>
 * Created by ZXFeng on 2021/11/26.
 */
@Slf4j
public class TestWebController {

    @FXML
    TabPane myPane;
    @FXML
    WebView staticWeb;
    @FXML
    WebView dynamicWeb;

    @FXML
    private void initialize() {
        String html = """
                <html>
                    <h1>Test H1</h1>
                    <b>Test 1234</b> <br />
                    <a href="https://www.baidu.com">BaiDu 百度</a>
                </html>
                """;
        staticWeb.getEngine().loadContent(html);
        staticWeb.prefWidthProperty().bind(myPane.widthProperty().multiply(0.98));
        staticWeb.prefHeightProperty().bind(myPane.heightProperty().multiply(0.90));

        dynamicWeb.getEngine().load("https://www.baidu.com");
        dynamicWeb.prefWidthProperty().bind(myPane.widthProperty().multiply(0.98));
        dynamicWeb.prefHeightProperty().bind(myPane.heightProperty().multiply(0.90));
    }

}
