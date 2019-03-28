package cn.zxf.neuroph.ssc.common.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.cj.jdbc.Driver;

public class MysqlUtil implements CommonConstant {

    private static Connection conn;

    /**
     * 获取连接(同一个线程共用)
     * 
     * @return
     */
    public static Connection getConnection() {
        if ( conn != null )
            return conn;
        return getLocalConnection();
    }

    /**
     * 获取本地连接
     * 
     * @return
     */
    private static Connection getLocalConnection() {
        try {

            Driver.class.getName();
            conn = DriverManager.getConnection( MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD );

            return conn;
        } catch ( SQLException e ) {
            e.printStackTrace();
        }

        return null;
    }

}
