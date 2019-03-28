package cn.zxf.btrace.demo.jstack.hash_table;

import static com.sun.btrace.BTraceUtils.jstack;
import static com.sun.btrace.BTraceUtils.println;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.Self;

// btrace -cp build <pid> TestHashTableScript.java
@BTrace
public class TestHashTableScript {

    /** 指明要查看的方法，类 */
    @OnMethod( //
            clazz = "java.util.Hashtable", //
            method = "put", //
            location = @Location( Kind.RETURN ) )
    public static void traceExecute( @Self java.util.Hashtable<?, ?> object ) {
        println( "---- print stack: " );
        jstack();
    }

}
