package cn.zxf.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Date;

public class GreetingTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform( //
            ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer //
    ) throws IllegalClassFormatException {
        if ( "cn/zxf/agent/test/Dog".equals( className ) ) {
            System.out.println( "\t  Dog's method invoke at\t" + new Date() );
        }
        return null;
    }

}