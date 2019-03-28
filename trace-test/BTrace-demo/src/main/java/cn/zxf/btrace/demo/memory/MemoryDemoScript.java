package cn.zxf.btrace.demo.memory;

import java.lang.management.MemoryUsage;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnLowMemory;

@BTrace
public class MemoryDemoScript {

    @OnLowMemory( //
            // pool = "Survivor Gen", //
            // pool = "Perm Gen", //
            // pool = "Tenured Gen", //
            pool = "G1 Eden", //
            threshold = 3000_000_000L //
    )
    public static void onLowMem( MemoryUsage mu ) {
        BTraceUtils.println( mu );
        BTraceUtils.println( " -------------- " );
    }

}
