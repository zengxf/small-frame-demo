package cn.zxf.btrace.demo.sample;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;
import com.sun.btrace.annotations.Sampled;

@BTrace
public class SampledDemoScript {

    @OnMethod( clazz = "/.*ProbeDemo/", location = @Location( Kind.RETURN ) )
    @Sampled // ( kind = Sampler.Adaptive )
    public static void duration( //
            @ProbeClassName String pcn, //
            @ProbeMethodName String pmn //
    ) {
        BTraceUtils.println( "class-name: " + pcn );
        BTraceUtils.println( "method-name: " + pmn );
        BTraceUtils.println( " ---------- " );
    }

}
