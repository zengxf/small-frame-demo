package test.string.encrypt.detail;

public class DESEncryptTest {

    public static void main( String[] args ) throws Exception {
        String key = "abcabc======$$$$$$111000";
        DESEncrypt encrypt = new DESEncrypt();

        encrypt.initialize_encryptKey( key );
        String root = "D:/test/md5/";
        encrypt.encrypt( root + "a.txt", root + "b.txt" );

        encrypt.initalize_dencryptkey( key );
        encrypt.decrypt( root + "b.txt", root + "a1.txt" );
    }

}
