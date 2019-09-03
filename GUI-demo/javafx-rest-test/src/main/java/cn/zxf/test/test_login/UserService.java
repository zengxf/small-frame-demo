package cn.zxf.test.test_login;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.Map;

/**
 * Created by zengxf on 2019/9/3.
 */
public interface UserService {

    @GET("/api/user/addition/find-one")
    Call<Map<String, Object>> findOne();

}
