package test;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

/**
 * <p/>
 * Created by ZXFeng on 2024/1/29
 */
public class JmhTest {

    // -Djava.io.tmpdir=D:\Data\Temp   // 可以不设置
    @Benchmark
    public void test1(Blackhole bh) {
        bh.consume(5);
    }

}
