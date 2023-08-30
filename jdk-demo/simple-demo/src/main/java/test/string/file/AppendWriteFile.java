package test.string.file;

import java.io.FileWriter;
import java.io.IOException;

public class AppendWriteFile {
    public static void main( String[] args ) throws IOException {
        String file = "C:/Users/Administrator/Desktop/test.txt";
        FileWriter fw = new FileWriter( file, true );
        fw.write( "bbb" );
        fw.flush();
        fw.close();
    }
}
