package cn.zxf.asm.demo3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class TestModifyClass {
    public static void main( String[] args ) {
	try {
	    String className = ReferenceClass.class.getName().replace( ".", "/" );

	    ClassReader cr = new ClassReader( className );
	    ClassWriter cw = new ClassWriter( ClassWriter.COMPUTE_MAXS );
	    ClassVisitor classAdapter = new AddTimeClassAdapter( cw );
	    // 使给定的访问者访问Java类的ClassReader
	    cr.accept( classAdapter, ClassReader.SKIP_DEBUG );

	    byte[] data = cw.toByteArray();
	    File file = new File( "D:/ReferenceClass.class" );
	    FileOutputStream fout = new FileOutputStream( file );
	    fout.write( data );
	    fout.close();
	    System.out.println( "success!" );
	} catch ( FileNotFoundException e ) {
	    e.printStackTrace();
	} catch ( IOException e ) {
	    e.printStackTrace();
	}
    }
}
