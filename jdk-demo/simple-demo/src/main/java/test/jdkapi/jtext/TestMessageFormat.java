package test.jdkapi.jtext;

import java.text.MessageFormat;
import java.util.Date;


/**
 * 从 0 开始
 * 
 * <p>
 * Created by zengxf on 2017-11-28
 */
public class TestMessageFormat {
    public static void main( String[] args ) {
        String template = "{0} has {1} messages";
        String message = MessageFormat.format( template, "Pierre", 42 );
        System.out.println( message );

        String fmt = "At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.";
        String event = "a disturbance in the Force";
        String result = MessageFormat.format( fmt, 7, new Date(), event );
        System.out.println( result );
    }
}
