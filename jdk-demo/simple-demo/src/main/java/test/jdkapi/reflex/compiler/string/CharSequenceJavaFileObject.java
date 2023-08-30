package test.jdkapi.reflex.compiler.string;

import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * 用于将java源码保存在content属性中
 */
public class CharSequenceJavaFileObject extends SimpleJavaFileObject {

    private String content;

    public CharSequenceJavaFileObject( String className, String content ) {
        super( URI.create( "string:///" + className.replace( '.', '/' ) + Kind.SOURCE.extension ), Kind.SOURCE );
        this.content = content;
    }

    @Override
    public String getCharContent( boolean ignoreEncodingErrors ) {
        return content;
    }
}