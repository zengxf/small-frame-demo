package cn.zxf.test.test_login;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by zengxf on 2019/9/3.
 */
@Slf4j
public class TestLoginSuccessController {

    @FXML
    private TextField nick;

    public void onExit() throws IOException {
        log.info("退出");
        System.exit(0);
    }

    public void onGetInfo() throws IOException {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("authentication-token", System.getProperty("token"))
                            .build();
                    return chain.proceed(request);
                })
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

        UserService service = retrofit.create(UserService.class);
        Call<Map<String, Object>> call = service.findOne();
        Map<String, Object> res = call.execute().body();
        log.info("res: {}", res);
        nick.setText(res.get("nick").toString());
    }

}
