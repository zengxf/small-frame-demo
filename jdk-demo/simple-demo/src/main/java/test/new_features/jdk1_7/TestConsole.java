package test.new_features.jdk1_7;

import java.io.Console;
import java.io.PrintWriter;

/**
 * 只能用在标准输入、输出流未被重定向的原始控制台中使用，在 Eclipse 或者其他 IDE 的控制台是用不了的
 * 
 * @author zengxf
 */
// cd /d F:\project\MyProject\mars\SimpleDemo\bin
// java cn.simple.test.new_features.jdk17.TestConsole
public class TestConsole {

    public static void main( String[] args ) {
	Console cons = System.console();
	if ( cons != null ) {
	    PrintWriter writer = cons.writer();
	    writer.write( "user:" );
	    cons.flush();

	    String user = cons.readLine();

	    writer.write( "password: " );
	    cons.flush();

	    char[] pwdArr = cons.readPassword();
	    String pwd = new String( pwdArr );

	    cons.format( "user: %s, pwd: %s", user, pwd );
	}
    }

}