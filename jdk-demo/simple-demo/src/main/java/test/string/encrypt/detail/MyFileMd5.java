package test.string.encrypt.detail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyFileMd5 {

    public static int           STREAM_BUFFER_LENGTH = 1024 * 10;

    private static final char[] DIGITS_LOWER         =                                         //
            { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static String fileMd5( String filePath ) {
        try {
            InputStream fileStream = new FileInputStream( filePath );
            MessageDigest digest = MessageDigest.getInstance( "MD5" );

            final byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
            int read = fileStream.read( buffer, 0, STREAM_BUFFER_LENGTH );

            while ( read > -1 ) {
                digest.update( buffer, 0, read );
                read = fileStream.read( buffer, 0, STREAM_BUFFER_LENGTH );
            }
            fileStream.close();

            byte[] byteArr = digest.digest();
            char[] charArr = encodeHex( byteArr );
            String md5 = new String( charArr );

            return md5;
        } catch ( NoSuchAlgorithmException e ) {
            e.printStackTrace();
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        return null;
    }

    private static char[] encodeHex( final byte[] data ) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        for ( int i = 0, j = 0; i < l; i++ ) {
            out[j++] = DIGITS_LOWER[( 0xF0 & data[i] ) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return out;
    }

}
