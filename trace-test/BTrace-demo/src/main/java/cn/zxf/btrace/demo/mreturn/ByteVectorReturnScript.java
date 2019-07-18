package cn.zxf.btrace.demo.mreturn;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.Return;

// Test jdk11 `ByteVectorImpl#getData()`
@BTrace( unsafe = true )
public class ByteVectorReturnScript {

    static int sign = 1;

    @OnMethod( clazz = "jdk.internal.reflect.ByteVectorImpl", method = "getData", location = @Location( Kind.RETURN ) )
    public static void duration( //
            @Return byte[] ret //
    ) {
        String file = String.format( "class-%d.class", sign++ );
        BTraceUtils.println( file + " => length: " + ret.length );
        try {
            Files.write( Paths.get( "C:\\Users\\Administrator\\Desktop\\aa\\" + file ), ret );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        BTraceUtils.println();
        BTraceUtils.println( " ---------- " );
    }

}
