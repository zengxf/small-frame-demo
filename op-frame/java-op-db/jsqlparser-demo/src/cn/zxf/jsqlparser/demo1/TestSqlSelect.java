package cn.zxf.jsqlparser.demo1;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;

@Slf4j
public class TestSqlSelect {
    public static void main( String[] args ) throws JSQLParserException {
	String sql = "select id, name, passwd from user";

	Statements stts = CCJSqlParserUtil.parseStatements( sql );
	List<Statement> statList = stts.getStatements();
	log.info( "{}", statList.size() );
	log.info( "{}", statList );
	statList.forEach( stat -> {
	    log.info( "class: {}", stat.getClass() );
	} );

	Statement stat = CCJSqlParserUtil.parse( sql );
	Select select = (Select) stat;
	SelectBody selectBody = select.getSelectBody();
	log.info( "select body: {}", selectBody );
	log.info( "select body: {}", selectBody.getClass() );

	PlainSelect plainSelect = (PlainSelect) selectBody;
	plainSelect.getSelectItems().forEach( item -> {
	    log.info( "item: {}", item );
	    log.info( "item: {}", item.getClass() );
	    SelectExpressionItem exp = (SelectExpressionItem) item;
	    log.info( "exp alias: {}", exp.getAlias() );

	    Expression expression = exp.getExpression();
	    log.info( "exp exp: {}", expression );
	    log.info( "exp exp: {}", expression.getClass() );
	    Column col = (Column) expression;
	    log.info( "col.table: {}", col.getTable() );
	    log.info( "col.name: {}", col.getColumnName() );
	} );
	;
    }
}
