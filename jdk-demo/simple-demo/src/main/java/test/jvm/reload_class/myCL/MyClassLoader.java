package test.jvm.reload_class.myCL;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyClassLoader extends ClassLoader {

    private String root = "D:/test/md5/";

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
	System.out.println( "klass == " + klass );
	return klass;
    }

    private byte[] getClassBytes( String className ) throws IOException {
	String path = root;
	path += className.substring( className.lastIndexOf( "." ) + 1 ) + ".class";
	System.out.println( path );
	FileInputStream fis = null;
	try {
	    fis = new FileInputStream( path );
	} catch ( FileNotFoundException e ) {
	    System.out.println( e );
	    return null;
	}
	byte[] bs = new byte[fis.available()];
	fis.read( bs );
	fis.close();
	return bs;
    }
}
