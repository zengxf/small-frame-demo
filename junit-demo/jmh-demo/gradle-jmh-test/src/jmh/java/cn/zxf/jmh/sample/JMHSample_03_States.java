package cn.zxf.jmh.sample;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class JMHSample_03_States {

    @State( Scope.Benchmark )
    public static class BenchmarkState {
        volatile double x = Math.PI;
    }

    @State( Scope.Thread )
    public static class ThreadState {
        
        @Param( { "1", "2", "3" } )
        int             testNum;

        volatile double x = Math.PI;

        @Setup
        public void prepare() {
            x = Math.PI;
        }

        @TearDown
        public void check() {
            assert x > Math.PI : "Nothing changed?";
        }
    }

    @Benchmark
    public void measureUnshared( ThreadState state ) {
        state.x++;
    }

    @Benchmark
    public void measureShared( BenchmarkState state ) {
        state.x++;
    }

    public static void main( String[] args ) throws RunnerException {
        Options opt = new OptionsBuilder().include( JMHSample_03_States.class.getSimpleName() )
                .threads( 4 )
                .forks( 1 )
                .build();
        new Runner( opt ).run();
    }

}
