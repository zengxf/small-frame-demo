package cn.zxf.test.test_login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.Map;

/**
 * Created by zengxf on 2019/9/3.
 */
public interface LoginService {

    @POST("/api/security/login")
    Call<Map<String, Object>> login(@Body Map<String, Object> body);

}
