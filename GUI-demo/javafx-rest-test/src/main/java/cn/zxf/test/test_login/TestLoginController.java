package cn.zxf.test.test_login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

/**
 * Created by zengxf on 2019/9/3.
 */
@Slf4j
public class TestLoginController {

    @FXML
    private TextField uname;
    @FXML
    private PasswordField pwd;
    @FXML
    private CheckBox remember;
    @FXML
    private Parent pane;

    public void onLogin() throws IOException {
        log.info(System.getProperty("user.dir"));
        log.info(uname.getText());
        log.info(pwd.getText());
        log.info("{}", remember.isSelected());

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(
                        new HttpLoggingInterceptor((msg) -> log.info(msg))
                                .setLevel(HttpLoggingInterceptor.Level.HEADERS)
                );
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8899")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

        LoginService service = retrofit.create(LoginService.class);
        Map<String, Object> body = Map.of(
                "account", uname.getText(),
                "password", pwd.getText(),
                "remember", remember.isSelected()
        );
        Call<Map<String, Object>> call = service.login(body);
        Map<String, Object> res = call.execute().body();
        log.info("{}", res);
        if (Objects.equals("ok", res.get("login"))) {
            System.setProperty("token", res.get("token").toString());
            this.show("/test-login-success.fxml", "登录成功");
            pane.getScene().getWindow().hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("消息提示");
            alert.setHeaderText("登录出错");
            alert.setContentText("用户不存在或密码错误");
            alert.showAndWait();
        }
    }

    private void show(String urlStr, String title) {
        try {
            URL url = TestLoginController.class.getResource(urlStr);
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
