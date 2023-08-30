package test.string.encrypt.digest;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestSHA_256 {

    public static void main( String[] args ) throws NoSuchAlgorithmException, IOException {
        String password = "123456";

        MessageDigest md = MessageDigest.getInstance( "SHA-256" );
        byte[] hashInBytes = md.digest( password.getBytes() );

        String sha = bytesToHex( hashInBytes );
        System.out.println( sha ); // SHA-256bit 32B 64Str

        String filePath = "C:\\Users\\Administrator\\Desktop\\aa\\test1.jpg";
        byte[] checksum = checksum( filePath );
        String fileSha = bytesToHex( checksum );
        System.out.println( fileSha ); // File-SHA-256
    }

    private static byte[] checksum( String filepath ) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance( "SHA-256" );
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
