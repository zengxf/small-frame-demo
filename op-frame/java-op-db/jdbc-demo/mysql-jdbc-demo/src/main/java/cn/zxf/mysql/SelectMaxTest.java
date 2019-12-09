package cn.zxf.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectMaxTest {

    public static void main( String[] args ) {
        Connection conn = ConnectionUtil.getLocalConnection();
        try {
            String sql = "SELECT MAX(web_lv2_sign) max_lv2 FROM crawler_image_log WHERE website = 'mm131' AND is_test <> 0";
            PreparedStatement stmt = conn.prepareStatement( sql );
            ResultSet res = stmt.executeQuery();
            if ( res.next() ) {
                System.out.println( res.getInt( "max_lv2" ) );
            }
            System.out.println( "done" );
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

}
