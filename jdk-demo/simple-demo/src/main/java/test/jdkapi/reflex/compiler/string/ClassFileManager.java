package test.jdkapi.reflex.compiler.string;

import java.io.IOException;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

/**
 * 类文件管理器 用于JavaCompiler将编译好后的class，保存到jclassObject中
 */
public class ClassFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

    private JavaClassObject jclassObject;

    public ClassFileManager( StandardJavaFileManager standardManager ) {
        super( standardManager );
    }

    @Override
    public JavaFileObject getJavaFileForOutput( Location location, String className, JavaFileObject.Kind kind, FileObject sibling ) throws IOException {
        if ( jclassObject == null )
            jclassObject = new JavaClassObject( className, kind );
        return jclassObject;
    }

    public JavaClassObject getJavaClassObject() {
        return jclassObject;
    }
}
