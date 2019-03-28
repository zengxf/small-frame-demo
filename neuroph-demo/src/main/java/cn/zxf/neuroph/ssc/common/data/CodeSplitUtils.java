package cn.zxf.neuroph.ssc.common.data;

import java.sql.SQLException;
import java.util.List;

import cn.zxf.neuroph.ssc.common.db.OpenRecordDao;

/**
 * 码号分开的
 * 
 * <p>
 * Created by zengxf on 2017-11-21
 */
public class CodeSplitUtils {

    static final OpenRecordDao dao     = new OpenRecordDao();
    static final String        SQL_FMT = "SELECT  CONCAT_WS('-', yyyy, mm, dd) date, period, n1, n2, n3, n4, n5 FROM ssc_open_record %s ORDER BY yyyy, mm, dd, period";

    public static List<CodeSplitVo> find( ParamVo param ) {
        String sql = String.format( SQL_FMT, param.toWhere() );
        List<CodeSplitVo> rows = dao.find( sql, res -> {
            try {
                return CodeSplitVo.of( //
                        res.getString( "date" ), //
                        res.getInt( "period" ), //
                        res.getDouble( "n1" ), //
                        res.getDouble( "n2" ), //
                        res.getDouble( "n3" ), //
                        res.getDouble( "n4" ), //
                        res.getDouble( "n5" ) );
            } catch ( SQLException e ) {
                throw new RuntimeException( "解析数据出错！", e );
            }
        } );
        return rows;
    }

}
