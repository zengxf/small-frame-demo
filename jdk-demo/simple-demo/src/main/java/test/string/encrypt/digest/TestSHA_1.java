package test.string.encrypt.digest;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestSHA_1 {

    public static void main( String[] args ) throws NoSuchAlgorithmException, IOException {
        String password = "123456";

        MessageDigest md = MessageDigest.getInstance( "SHA-1" );
        byte[] hashInBytes = md.digest( password.getBytes() );

        String sha1 = bytesToHex( hashInBytes );
        System.out.println( sha1 ); // SHA-1(160bit) 20B 40Str

        String filePath = "C:\\Users\\Administrator\\Desktop\\aa\\test1.jpg";
        byte[] checksum = checksum( filePath );
        String fileSha1 = bytesToHex( checksum );
        System.out.println( fileSha1 ); // File-SHA-1
    }

    private static byte[] checksum( String filepath ) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance( "SHA-1" );
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
