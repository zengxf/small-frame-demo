package test.string.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class WriteFile {
    public static void main( String[] args ) throws IOException {
        String file = "C:/Users/Administrator/Desktop/test.txt";
        FileOutputStream fis = new FileOutputStream( file );
        OutputStreamWriter osw = new OutputStreamWriter( fis, "UTF8" );
        osw.write( "test 1, 中要" );
        osw.flush();
        osw.close();
    }
}
