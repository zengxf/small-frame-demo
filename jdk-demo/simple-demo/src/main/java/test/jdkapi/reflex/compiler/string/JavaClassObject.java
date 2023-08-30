package test.jdkapi.reflex.compiler.string;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * 将输出流交给JavaCompiler，最后JavaCompiler将编译后的class文件写入输出流中
 */
public class JavaClassObject extends SimpleJavaFileObject {

    protected final ByteArrayOutputStream bos = new ByteArrayOutputStream();

    public JavaClassObject( String name, Kind kind ) {
        super( URI.create( "string:///" + name.replace( '.', '/' ) + kind.extension ), kind );
    }

    public byte[] getBytes() {
        return bos.toByteArray();
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return bos;
    }

    @SuppressWarnings( "deprecation" )
    @Override
    protected void finalize() throws Throwable {
        try {
            bos.close();
        } finally {
            super.finalize();
        }
    }
}
