package test.jdkapi.reflex.compiler.string;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

public class DynamicEngine {
    private static DynamicEngine ourInstance = new DynamicEngine();

    public static DynamicEngine getInstance() {
        return ourInstance;
    }

    private URLClassLoader parentClassLoader;
    private String         classpath;

    private DynamicEngine() {
        // 获取类加载器
        this.parentClassLoader = (URLClassLoader) this.getClass()
                .getClassLoader();
        // 创建classpath
        this.buildClassPath();
    }

    private void buildClassPath() {
        this.classpath = null;
        StringBuilder sb = new StringBuilder();
        for ( URL url : this.parentClassLoader.getURLs() ) {
            String p = url.getFile();
            sb.append( p )
                    .append( File.pathSeparator );
        }
        this.classpath = sb.toString();
    }

    // ---------------------
    // ---------------------
    // ---------------------

    public Object javaCodeToObject( String fullClassName, String javaCode ) throws Exception {
        long start = System.currentTimeMillis(); // 记录开始编译时间
        Object instance = null;
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

        ClassFileManager fileManager = new ClassFileManager( compiler.getStandardFileManager( diagnostics, null, null ) );

        List<JavaFileObject> jfiles = new ArrayList<JavaFileObject>();
        jfiles.add( new CharSequenceJavaFileObject( fullClassName, javaCode ) );

        List<String> options = new ArrayList<String>();
        options.add( "-encoding" );
        options.add( "UTF-8" );
        options.add( "-classpath" );
        options.add( this.classpath );

        JavaCompiler.CompilationTask task = compiler.getTask( null, fileManager, diagnostics, options, null, jfiles );
        // 编译源程序
        boolean success = task.call();

        if ( success ) {
            // 如果编译成功，用类加载器加载该类
            JavaClassObject jco = fileManager.getJavaClassObject();
            DynamicClassLoader classLoader = new DynamicClassLoader( this.parentClassLoader );
            Class<?> clazz = classLoader.loadClass( fullClassName, jco );
            instance = clazz.getDeclaredConstructor()
                    .newInstance();
            classLoader.close();
        } else {
            // 如果想得到具体的编译错误，可以对Diagnostics进行扫描
            String error = "";
            for ( Diagnostic<?> diagnostic : diagnostics.getDiagnostics() ) {
                error += compilePrint( diagnostic );
            }
            System.out.println( "error: " + error );
        }
        long end = System.currentTimeMillis();
        System.out.println( "javaCodeToObject use: " + ( end - start ) + "ms" );
        return instance;
    }

    // ---------------------
    // ---------------------
    // ---------------------

    private String compilePrint( Diagnostic<?> diagnostic ) {
        StringBuffer res = new StringBuffer();
        res.append( "Code:[" + diagnostic.getCode() + "]\n" );
        res.append( "Kind:[" + diagnostic.getKind() + "]\n" );
        res.append( "Position:[" + diagnostic.getPosition() + "]\n" );
        res.append( "Start Position:[" + diagnostic.getStartPosition() + "]\n" );
        res.append( "End Position:[" + diagnostic.getEndPosition() + "]\n" );
        res.append( "Source:[" + diagnostic.getSource() + "]\n" );
        res.append( "Message:[" + diagnostic.getMessage( null ) + "]\n" );
        res.append( "LineNumber:[" + diagnostic.getLineNumber() + "]\n" );
        res.append( "ColumnNumber:[" + diagnostic.getColumnNumber() + "]\n" );
        return res.toString();
    }
}