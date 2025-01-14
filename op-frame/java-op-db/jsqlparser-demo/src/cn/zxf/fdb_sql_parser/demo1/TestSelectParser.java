package cn.zxf.fdb_sql_parser.demo1;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.CursorNode;
import com.foundationdb.sql.parser.SQLParser;
import com.foundationdb.sql.parser.StatementNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestSelectParser {
    public static void main( String[] args ) throws StandardException {
	SQLParser parser = new SQLParser();
	StatementNode stmt = parser.parseStatement( "select userid,username,password from sys_user where username = 'isea533'" );
	// stmt.treePrint();
	log.info( "{}", stmt.getClass() );

	CursorNode node = (CursorNode) stmt;
	log.info( "{}", node );

    }
}
