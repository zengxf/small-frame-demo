package cn.zxf.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 测试 - 通过 SPI 获取连接 <br>
 * 不再需要调用 Class.forName("com.mysql.jdbc.Driver")
 * 
 * <p>
 * Created by zengxf on 2018-01-02
 */
public class TestConnBySpi {

    public static void main( String[] args ) {
        Connection conn = getLocalConnection();
        System.out.println( conn );
    }

    static String url      = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&useSSL=false";
    static String user     = "root";
    static String password = "abc";

    static Connection getLocalConnection() {
        try {
            Connection conn = DriverManager.getConnection( url, user, password );
            return conn;
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        return null;
    }

    static Connection getLocalConnectionByOld() {
        try {
            Class.forName( "com.mysql.cj.jdbc.Driver" ); // 手动初始化类
            Connection conn = DriverManager.getConnection( url, user, password );
            return conn;
        } catch ( SQLException | ClassNotFoundException e ) {
            e.printStackTrace();
        }
        return null;
    }

}
