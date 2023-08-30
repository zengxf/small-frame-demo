package test.jvm.reload_class.netCL;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class NetworkClassLoader extends ClassLoader {

    private String rootUrl;

    public NetworkClassLoader( String rootUrl ) {
	this.rootUrl = rootUrl;
    }

    @Override
    protected Class<?> findClass( String name ) throws ClassNotFoundException {
	Class<?> clazz = null;
	byte[] classData = getClassData( name ); // 鏍规嵁绫荤殑浜岃繘鍒跺悕绉�,鑾峰緱璇lass鏂囦欢鐨勫瓧鑺傜爜鏁扮粍
	if ( classData == null ) {
	    throw new ClassNotFoundException();
	}
	clazz = defineClass( name, classData, 0, classData.length ); // 灏哻lass鐨勫瓧鑺傜爜鏁扮粍杞崲鎴怌lass绫荤殑瀹炰緥
	return clazz;
    }

    private byte[] getClassData( String name ) {
	InputStream is = null;
	try {
	    String path = classNameToPath( name );
	    System.out.println( "loader path: " + path );
	    URL url = new URL( path );
	    byte[] buff = new byte[1024 * 4];
	    int len = -1;
	    is = url.openStream();
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    while ( ( len = is.read( buff ) ) != -1 ) {
		baos.write( buff, 0, len );
	    }
	    return baos.toByteArray();
	} catch ( Exception e ) {
	    e.printStackTrace();
	} finally {
	    if ( is != null ) {
		try {
		    is.close();
		} catch ( IOException e ) {
		    e.printStackTrace();
		}
	    }
	}
	return null;
    }

    private String classNameToPath( String name ) {
	String urlStr = String.format( "%s/%s/%s.class", rootUrl, "test", name.substring( name.lastIndexOf( "." ) + 1 ) );
	return urlStr;
    }

}
