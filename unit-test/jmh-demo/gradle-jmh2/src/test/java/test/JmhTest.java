package test;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * <p/>
 * Created by ZXFeng on 2024/1/29
 */
@BenchmarkMode({Mode.AverageTime})
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(time = 1, iterations = 5)
@Measurement(iterations = 5)
@Threads(4)
@Fork(1)
public class JmhTest {

    // -Djava.io.tmpdir=D:\Data\Temp   // 可以不设置
    @Benchmark
    public void test1(Blackhole bh) {
        bh.consume(5);
    }

    @Benchmark
    public void test2() {
        NumUtils.sum(1, 2, 3, 4);
    }

}
