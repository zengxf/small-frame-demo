package cn.zxf.btrace.demo.mreturn;

import java.io.IOException;
import java.util.Arrays;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.Return;

@BTrace( unsafe = true )
public class ReturnDemoScript {

    @OnMethod( clazz = "/.*ReturnDemo/", method = "test", location = @Location( Kind.RETURN ) )
    public static void duration( //
            @Return byte[] ret //
    ) {
        BTraceUtils.println( "ret-v: " + Arrays.toString( ret ) );
        try {
            java.nio.file.Files.write( java.nio.file.Paths.get( "C:\\Users\\Administrator\\Desktop\\aa\\test.txt" ), ret );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        BTraceUtils.println();
        BTraceUtils.println( " ---------- " );
    }

}
