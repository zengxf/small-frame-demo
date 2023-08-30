package test.string.js_json;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadText {

    public static String readTxt( String path, String charset ) throws IOException {
        StringBuilder sb = new StringBuilder();

        InputStream fis = new FileInputStream( path );
        InputStreamReader isr = new InputStreamReader( fis, charset );
        BufferedReader br = new BufferedReader( isr );

        String line = br.readLine();
        while ( line != null ) {
            sb.append( line ).append( "\n" );
            line = br.readLine();
        }

        br.close();

        return sb.toString();
    }

}
