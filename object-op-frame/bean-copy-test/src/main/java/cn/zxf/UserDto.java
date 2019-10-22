package cn.zxf;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class UserDto {

    private String        id;
    private Integer       age;
    private Integer       status;
    private List<Integer> codes;
    // private cn.zxf.User.LoginLog latestLog; // CGLib 它会创建对应的字节码，只是简单的浅复制
    private LoginLogDto   latestLog; // Apache 的报错

    @Data
    public static class LoginLogDto {
        private Date   loginDate;
        private String ip;
    }

}
