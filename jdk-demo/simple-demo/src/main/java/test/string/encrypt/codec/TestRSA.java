package test.string.encrypt.codec;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

public class TestRSA {
    public static void main( String[] args ) throws Exception {
        // generate public and private keys
        KeyPair keyPair = buildKeyPair();
        PublicKey pubKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        System.out.println( "公钥  key: " + pubKey );
        System.out.println( "私钥  key: " + privateKey );

        // sign the message
        byte[] signed = encrypt( privateKey, "测试用的消息 - test message" );
        System.out.println( "加密后的 Str: " + Base64.getEncoder()
                .encodeToString( signed ) ); // <<signed message>>

        // verify the message
        byte[] verified = decrypt( pubKey, signed );
        System.out.println( "解密后的 Str: " + new String( verified ) ); // This is a secret message
    }

    public static KeyPair buildKeyPair() throws NoSuchAlgorithmException {
        final int keySize = 1024;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance( "RSA" );
        keyPairGenerator.initialize( keySize );
        return keyPairGenerator.genKeyPair();
    }

    public static byte[] encrypt( PrivateKey privateKey, String message ) throws Exception {
        Cipher cipher = Cipher.getInstance( "RSA" );
        cipher.init( Cipher.ENCRYPT_MODE, privateKey );
        return cipher.doFinal( message.getBytes() );
    }

    public static byte[] decrypt( PublicKey publicKey, byte[] encrypted ) throws Exception {
        Cipher cipher = Cipher.getInstance( "RSA" );
        cipher.init( Cipher.DECRYPT_MODE, publicKey );
        return cipher.doFinal( encrypted );
    }
}
