package cn.zxf.btrace.demo.exit;

import static com.sun.btrace.BTraceUtils.println;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.BTraceUtils.Sys;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnExit;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.OnTimer;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;

@BTrace
public class ExitDemoScript {

    private static volatile int i;

    @OnMethod( clazz = "/.*ExitDemo/", location = @Location( Kind.RETURN ) )
    public static void duration( //
            @ProbeClassName String pcn, //
            @ProbeMethodName String pmn //
    ) {
        BTraceUtils.println( "class-name: " + pcn );
        BTraceUtils.println( "method-name: " + pmn );
        BTraceUtils.println( " ---------- " );
    }

    @OnExit
    public static void onexit( int code ) {
        println( "BTrace program exits!" );
    }

    @OnTimer( 1000 )
    public static void ontime() {
        println( "hello" );
        i++;
        if ( i == 5 ) {
            Sys.exit( 0 );
        }
    }
}
