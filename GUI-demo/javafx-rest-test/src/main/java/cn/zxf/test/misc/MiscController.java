package cn.zxf.test.misc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;

public class MiscController {

    @FXML
    private TextField dateText;

    public void showDateTime(ActionEvent event) {
        String date = String.format("%tF %<tT", System.currentTimeMillis());
        System.out.println("date(日期-时间): " + date);
        dateText.setText(date);
    }

    public void onShowLogin() {
        this.show("/test-login.fxml", "登录 测试");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
