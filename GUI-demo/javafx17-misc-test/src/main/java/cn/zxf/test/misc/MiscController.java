package cn.zxf.test.misc;

import cn.zxf.test.test1.Test1Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;

@Slf4j
public class MiscController {

    @FXML
    private TextField dateText;

    public void showDateTime(ActionEvent event) {
        String date = String.format("%tF %<tT", System.currentTimeMillis());
        System.out.println("date(日期-时间): " + date);
        dateText.setText(date);
    }

    public void onShowTest1(ActionEvent event) {
        try {
            URL url = MiscController.class.getResource("/test-1.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);

            TabPane page = loader.load();
            Scene scene = new Scene(page);

            Test1Controller test1 = loader.getController(); // 要先 load 才有 controller
            test1.setValue(true);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("简单 Tab 测试");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(scene);
            dialogStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onShowTable(ActionEvent event) {
        this.show("/test-table.fxml", "表格 测试");
    }

    public void onShowChart() {
        this.show("/test-chart.fxml", "图表 测试");
    }

    public void onShowEmbed() {
        this.show("/test-embed.fxml", "嵌套 测试");
    }

    private void show(String urlStr, String title) {
        try {
            URL url = MiscController.class.getResource(urlStr);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);

            Parent page = loader.load();
            Scene scene = new Scene(page);

            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(scene);
            dialogStage.show();
            dialogStage.setOnCloseRequest(event -> {
                log.info("[{}] 关闭！", title);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
