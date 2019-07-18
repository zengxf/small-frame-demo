package cn.simple.test.new_features.jdk9.jl;

import java.util.Currency;
import java.util.ResourceBundle;
import java.util.Set;

import static java.lang.System.Logger.Level.*;

/**
 * Created by zengxf on 2017/10/9.
 */
public class TestPlatformLogger {

    public static void main(String[] arr) {
        Set<Currency> c = Currency.getAvailableCurrencies();
        System.out.println("# of currencies: " + c.size());

        System.Logger logger = System.getLogger("Log4jLogger");
        logger.log(TRACE, "Entering application.");
        logger.log(ERROR, "An unknown error occurred.");
        logger.log(INFO, "FYI");
        logger.log(TRACE, "Exiting application.");
        logger.log(TRACE, "format test: %s, %s.", "a", "b");
    }

    public static class Log4jLoggerFinder extends System.LoggerFinder {
        private final Log4jLogger logger = new Log4jLogger();

        @Override
        public System.Logger getLogger(String name, Module module) {
            System.out.printf("Log4jLoggerFinder.getLogger(): " + "[name=%s, module=%s]%n", name, module.getName());
            return logger;
        }
    }

    static class Log4jLogger implements System.Logger {

        @Override
        public String getName() {
            return "Log4jLogger";
        }

        @Override
        public boolean isLoggable(Level level) {
            return true;
        }

        @Override
        public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
            System.out.printf("log 1 -> Lv: [%s], Msg: [%s]%n", level, msg);
        }

        @Override
        public void log(Level level, ResourceBundle bundle, String format, Object... params) {
            System.out.printf("log 2 -> Lv: [%s], Msg: [%s]%n", level, String.format(format, params));
        }
    }

}
