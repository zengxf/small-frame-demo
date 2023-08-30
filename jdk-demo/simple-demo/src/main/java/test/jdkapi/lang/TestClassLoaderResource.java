package test.jdkapi.lang;

import java.net.URL;
import java.util.Enumeration;

// 寻找 Array.class 文件
public class TestClassLoaderResource {

    public static void main( String[] args ) throws Exception {
        // Array.class 的完整路径
        String name = "java/sql/Array.class";
        Enumeration<URL> urls = Thread.currentThread()
                .getContextClassLoader()
                .getResources( name );
        while ( urls.hasMoreElements() ) {
            URL url = urls.nextElement();
            System.out.println( url.toString() );
        }
    }

}
