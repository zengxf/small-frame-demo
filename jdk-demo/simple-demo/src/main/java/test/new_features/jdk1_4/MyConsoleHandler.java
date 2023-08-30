package test.new_features.jdk1_4;

import java.util.logging.ConsoleHandler;

public class MyConsoleHandler extends ConsoleHandler {

    public MyConsoleHandler() {
	super.setOutputStream( System.out );
    }

}
