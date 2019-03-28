package cn.zxf.agent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * Java SE 6
 * 
 * <p>
 * Created by zengxf on 2018-02-07
 */
public class GreetingAgent3 {

    public static void premain( //
            String agentArgs, Instrumentation inst //
    ) throws ClassNotFoundException, UnmodifiableClassException, InterruptedException {
        inst.addTransformer( new GreetingTransformer3(), true );
        inst.retransformClasses( GreetingTransformer3.class );
        System.out.println( "Agent Main Done" );
    }

    public static void agentmain( //
            String agentArgs, Instrumentation inst //
    ) throws ClassNotFoundException, UnmodifiableClassException, InterruptedException {
        inst.addTransformer( new GreetingTransformer3(), true );
        inst.retransformClasses( GreetingTransformer3.class );
        System.out.println( "Agent Main Done" );
    }

}
