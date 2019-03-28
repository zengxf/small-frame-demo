package cn.zxf.druid.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.alibaba.druid.pool.DruidDataSource;

public class TestMysqlSelect {

    public static void main( String[] args ) throws SQLException {
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl( "jdbc:mysql://localhost:3306/test" );
        ds.setUsername( "root" );
        ds.setPassword( "admin" );

        Connection conn = ds.getConnection();
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery( "select * from test" );

        System.out.println( "--------------" );
        while ( rs.next() ) {
            System.out.printf( "%s - %s - %s %n", rs.getObject( 1 ), rs.getObject( 2 ), rs.getObject( 3 ) );
        }
        System.out.println( "--------------" );

        conn.close();
        ds.close();
    }

}
