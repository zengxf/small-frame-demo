package cn.zxf.jmh;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class TestHelloMain {

    public static void main( String[] args ) throws RunnerException {
        String regex = ".*" + TestHello.class.getSimpleName() + ".*";
        System.out.println( regex );
        Options opt = new OptionsBuilder().include( regex )
                .forks( 1 )
                .build();
        new Runner( opt ).run();
    }

}
