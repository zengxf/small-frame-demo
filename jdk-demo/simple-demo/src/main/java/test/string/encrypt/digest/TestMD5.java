package test.string.encrypt.digest;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestMD5 {

    public static void main( String[] args ) throws NoSuchAlgorithmException, IOException {
        String password = "123456";

        MessageDigest md = MessageDigest.getInstance( "MD5" );
        byte[] hashInBytes = md.digest( password.getBytes() );

        String md5 = bytesToHex( hashInBytes );
        System.out.println( md5 ); // MD5-32(16B 128bit)
        System.out.println( md5.substring( 8, 24 ) ); // MD5-16

        String filePath = "C:\\Users\\Administrator\\Desktop\\aa\\test1.jpg";
        String fileMd5 = bytesToHex( checksum( filePath ) );
        System.out.println( fileMd5 ); // File-MD5-32
    }

    private static byte[] checksum( String filepath ) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance( "MD5" );
        try ( DigestInputStream dis = new DigestInputStream( new FileInputStream( filepath ), md ) ) {
            while ( dis.read() != -1 ) {
            }
            md = dis.getMessageDigest();
        }
        return md.digest();
    }

    private static String bytesToHex( byte[] hashInBytes ) {
        StringBuilder sb = new StringBuilder();
        for ( byte b : hashInBytes ) {
            sb.append( String.format( "%02x", b ) );
        }
        return sb.toString();
    }
    
}
