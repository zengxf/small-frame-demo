package test;

import feign.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * 用户接口测试，网站： https://gorest.co.in
 * <br/>
 * Created by ZXFeng on  2021/11/25.
 */
public interface UserService {

    @RequestLine("GET /public/v1/users?page={page}")
    ListDto findUsers(
            @Param("page") Integer page
    );

    @RequestLine("GET /public/v1/users")
    ListDto findUsers2(
            @QueryMap Map<String, Object> map
    );

    @Headers({
            "Content-Type: application/json; charset=utf-8", // 要加设置
            "Authorization: {token}"
    })
    @RequestLine("POST /public/v1/users")
    Object createUser(
            @Param("token") String token,
            UserDto dto
    );

    @Data
    class ListDto {
        Map<String, Object> meta;
        List<UserDto> data;
    }

    @Data
    @Accessors(chain = true)
    class UserDto {
        Integer id;
        String name;
        String gender;
        String email;
        String status;
        String errorInfo;
    }

}
