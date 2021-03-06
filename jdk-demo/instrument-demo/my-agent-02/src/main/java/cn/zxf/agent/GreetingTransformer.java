package cn.zxf.agent;

import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Date;

public class GreetingTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform( //
            ClassLoader loader, String className, Class<?> classBeingRedefined, //
            ProtectionDomain protectionDomain, byte[] classfileBuffer //
    ) throws IllegalClassFormatException {
        if ( "cn/zxf/agent/test/Dog".equals( className ) ) {
            System.out.println( "\t  Dog's method invoke at\t" + new Date() );
            byte[] bytecode = this.bytecode();
            System.out.println( "\t bytecode-length: " + bytecode.length );
            return bytecode;
        }
        return null;
    }

    private byte[] bytecode() {
        try {
            System.out.println( "\t  读取文件字节码！" );
            InputStream is = GreetingTransformer.class.getResource( "/classes/Dog.class.file" ).openStream();
            byte[] bytearr = new byte[is.available()];
            is.read( bytearr );
            return bytearr;
        } catch ( Exception e ) {
            System.out.println( "\t  读取文件出错：" + e.getMessage() );
            throw new RuntimeException( "读取 class 文件出错！", e );
        }
    }

}