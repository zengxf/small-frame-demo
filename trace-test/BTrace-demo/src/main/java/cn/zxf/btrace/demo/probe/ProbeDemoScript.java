package cn.zxf.btrace.demo.probe;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;

@BTrace
public class ProbeDemoScript {

    @OnMethod( clazz = "/.*ProbeDemo/", location = @Location( Kind.RETURN ) )
    public static void duration( //
            @ProbeClassName String pcn, //
            @ProbeMethodName String pmn //
    ) {
        BTraceUtils.println( "class-name: " + pcn );
        BTraceUtils.println( "method-name: " + pmn );
        BTraceUtils.println( " ---------- " );
    }

}
