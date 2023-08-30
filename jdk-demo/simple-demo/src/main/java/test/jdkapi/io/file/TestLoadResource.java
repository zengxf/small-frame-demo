package test.jdkapi.io.file;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class TestLoadResource {

    public static void main( String[] args ) throws IOException {
        loadFile();
    }

    static final String FACTORIES_RESOURCE_LOCATION = "META-INF/MANIFEST.MF";

    static void loadFile() throws IOException {
        // 取得资源文件的 URL
        Enumeration<URL> urls = TestLoadResource.class.getClassLoader()
                .getResources( FACTORIES_RESOURCE_LOCATION );
        // 遍历所有的 URL
        while ( urls.hasMoreElements() ) {
            URL url = urls.nextElement();
            System.out.println( url );
        }
    }
    
}
