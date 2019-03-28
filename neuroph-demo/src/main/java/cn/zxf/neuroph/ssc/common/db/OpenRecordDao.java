package cn.zxf.neuroph.ssc.common.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OpenRecordDao implements CommonConstant {

    public < T > List<T> find( String sql, PoTransformer<T> transformer ) {
        List<T> list = new ArrayList<>();
        try ( Connection conn = MysqlUtil.getConnection(); //
                Statement stmt = conn.createStatement(); //
                ResultSet res = stmt.executeQuery( sql ) ) {
            while ( res.next() ) {
                list.add( transformer.transform( res ) );
            }
        } catch ( SQLException e ) {
            throw new RuntimeException( "查询出错！sql: " + sql, e );
        }
        return list;
    }

}
