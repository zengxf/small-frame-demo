package cn.zxf;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors( chain = true )
public class User {

    private String       id;
    private String       sign;
    private Integer      age;
    private Integer      status;
    private LoginLog     latestLog;
    private List<String> codes;

    @Data
    @Accessors( chain = true )
    public static class LoginLog {
        private Date   loginDate;
        private String ip;
    }

    public static User testData() {
        LoginLog log = new LoginLog().setLoginDate( new Date() )
                .setIp( "127.0.0.1" );
        return new User().setId( "zxf-01" )
                .setSign( "test" )
                .setAge( 10 )
                .setStatus( 2 )
                .setCodes( List.of( "123", "234", "23" ) )
                .setLatestLog( log );
    }

}
