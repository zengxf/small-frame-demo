package test.url.resource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class GetRemoteResource {

    public static void main( String[] args ) throws IOException {
	URL url = new URL( "https://raw.githubusercontent.com/zengxf/GitTest/master/classes/test/TestFunction.class" );
	InputStream is = url.openConnection().getInputStream();
	FileOutputStream fos = new FileOutputStream( "D:/test/md5/bb.class" );

	int len = -1;
	byte[] arr = new byte[1024];
	while ( ( len = is.read( arr ) ) > -1 ) {
	    fos.write( arr, 0, len );
	}

	fos.close();
    }

}
