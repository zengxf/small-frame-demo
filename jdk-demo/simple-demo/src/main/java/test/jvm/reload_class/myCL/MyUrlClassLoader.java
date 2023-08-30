package test.jvm.reload_class.myCL;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MyUrlClassLoader extends ClassLoader {

    private String	 baseUrl = "https://raw.githubusercontent.com/zengxf/GitTest/master/classes";
    private final String sign;

    public MyUrlClassLoader() {
	sign = "test";
    }

    @Override
    protected Class<?> loadClass( String name, boolean resolve ) throws ClassNotFoundException {
	Class<?> klass = null;
	try {
	    klass = super.findLoadedClass( name ); // 检查该类是否已经被装载。
	    if ( klass != null ) {
		return klass;
	    }

	    byte[] bs = this.getClassBytes( name );// 从一个特定的信息源寻找并读取该类的字节。
	    if ( bs != null && bs.length > 0 ) {
		klass = super.defineClass( name, bs, 0, bs.length );
	    }
	    if ( klass == null ) { // 如果读取字节失败，则试图从JDK的系统API中寻找该类。
		klass = super.findSystemClass( name );
	    }
	    if ( resolve && klass != null ) {
		super.resolveClass( klass );
	    }
	} catch ( IOException e ) {
	    throw new ClassNotFoundException( e.toString() );
	}
	return klass;
    }

    private byte[] getClassBytes( String name ) throws IOException {
	if ( name.startsWith( "java." ) )
	    return null;

	String urlStr = String.format( "%s/%s/%s.class", baseUrl, sign, name.substring( name.lastIndexOf( "." ) + 1 ) );
	System.out.println( name + " -- " + urlStr );

	InputStream fis = null;
	try {
	    if ( !this.isValid( urlStr ) ) {
		System.out.println( "此URL不存在！" );
		return null;
	    }
	    URL url = new URL( urlStr );
	    URLConnection connt = url.openConnection();
	    fis = connt.getInputStream();
	} catch ( IOException e ) {
	    System.out.println( e );
	    return null;
	}

	byte[] bs = new byte[fis.available()];
	fis.read( bs );
	fis.close();
	return bs;
    }

    private boolean isValid( String strLink ) {
	URL url;
	try {
	    url = new URL( strLink );
	    HttpURLConnection connt = (HttpURLConnection) url.openConnection();
	    connt.setRequestMethod( "HEAD" );
	    String strMessage = connt.getResponseMessage();
	    if ( strMessage.compareTo( "Not Found" ) == 0 ) {
		return false;
	    }
	    connt.disconnect();
	} catch ( Exception e ) {
	    return false;
	}
	return true;
    }

}
