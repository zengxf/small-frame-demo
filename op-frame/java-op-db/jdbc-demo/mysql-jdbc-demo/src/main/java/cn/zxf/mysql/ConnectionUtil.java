package cn.zxf.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    /**
     * 获取本地连接
     * 
     * @return
     */
    public static Connection getLocalConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
            String user = "root";
            String password = "abc";
            Connection conn = DriverManager.getConnection( url, user, password );
            return conn;
        } catch ( SQLException e ) {
            throw new RuntimeException( "获取连接出错", e );
        }
    }

}
