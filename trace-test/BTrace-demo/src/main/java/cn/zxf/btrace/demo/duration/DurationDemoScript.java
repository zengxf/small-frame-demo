package cn.zxf.btrace.demo.duration;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Duration;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;

@BTrace
public class DurationDemoScript {

    @OnMethod( clazz = "/.*DurationDemo/", location = @Location( Kind.RETURN ) )
    public static void duration( @Duration long time ) {
        BTraceUtils.println( "use time: " + ( time / 1000_000L ) + " ms" );
    }

}
