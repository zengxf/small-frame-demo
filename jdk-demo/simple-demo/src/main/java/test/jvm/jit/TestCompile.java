package test.jvm.jit;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * JIT 编译测试
 * 
 * <pre>
 * 参数
 * 
 * -XX:CompileThreshold=1500 -XX:+PrintCompilation -XX:+CITime
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-09-26
 */
public class TestCompile {
    public static void main( String[] args ) {
        while ( true ) {
            IntStream.rangeClosed( 1, 100 ).boxed().collect( Collectors.toList() );
        }
    }
}
