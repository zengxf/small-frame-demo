package test.jdkapi.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class TestUrlConnection {

    public static void main( String[] args ) throws Exception {
        // System.setProperty( "javax.net.debug", "all" );
//        SslSetup.notVerfiy();

        URL url = new URL( "https://591234x.com/788789/31139.html" );
        // HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
        // https.setHostnameVerifier( SslSetup.HV );

        // InputStream is = url.openStream();
        // System.out.println( is );
        BufferedReader br = new BufferedReader( new InputStreamReader( url.openStream() ) );
        String line;
        while ( ( line = br.readLine() ) != null ) {
            System.out.println( line );
        }
    }

}
