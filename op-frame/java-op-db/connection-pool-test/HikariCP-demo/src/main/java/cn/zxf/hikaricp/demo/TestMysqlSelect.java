package cn.zxf.hikaricp.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class TestMysqlSelect {

    public static void main( String[] args ) throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl( "jdbc:mysql://localhost:3306/test" );
        config.setUsername( "root" );
        config.setPassword( "admin" );
        HikariDataSource ds = new HikariDataSource( config );

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
