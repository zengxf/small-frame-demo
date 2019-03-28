package cn.zxf.btrace.demo.jstack;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;

@BTrace
public class JStackDemoScript {

    @OnMethod( clazz = "cn.zxf.btrace.demo.jstack.JStackDemo", method = "test" )
    public static void traceOut() {
        BTraceUtils.println( "trace in out --- " );
        BTraceUtils.jstack();
    }

    @OnMethod( clazz = "cn.zxf.btrace.demo.jstack.JStackDemo$Test", method = "test" )
    public static void traceInner() {
        BTraceUtils.println( "trace in inner --- " );
        BTraceUtils.jstack();
    }

    @OnMethod( clazz = "java.util.ArrayList", method = "<init>" )
    public static void onnewThread() {
        BTraceUtils.println( "trace list new init --- " );
        BTraceUtils.jstack();
    }

}
