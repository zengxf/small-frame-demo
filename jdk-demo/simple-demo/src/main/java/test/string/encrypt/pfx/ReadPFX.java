package test.string.encrypt.pfx;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Base64;
import java.util.Enumeration;

public class ReadPFX {
    public ReadPFX() {
    }

    // 转换成十六进制字符串
    public String Byte2String( byte[] b ) {
        String hs = "";
        String stmp = "";

        for ( int n = 0; n < b.length; n++ ) {
            stmp = ( java.lang.Integer.toHexString( b[n] & 0XFF ) );
            if ( stmp.length() == 1 )
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    public byte[] StringToByte( int number ) {
        int temp = number;
        byte[] b = new byte[4];
        for ( int i = b.length - 1; i > -1; i-- ) {
            b[i] = Integer.valueOf( temp & 0xff )
                    .byteValue();// 将最高位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    public PrivateKey GetPvkformPfx( String strPfx, String strPassword ) {
        try {
            KeyStore ks = KeyStore.getInstance( "PKCS12" );
            FileInputStream fis = new FileInputStream( strPfx );
            char[] nPassword = null;
            if ( ( strPassword == null ) || strPassword.trim()
                    .equals( "" ) ) {
                nPassword = null;
            } else {
                nPassword = strPassword.toCharArray();
            }
            ks.load( fis, nPassword );
            fis.close();
            System.out.println( "keystore type=" + ks.getType() );
            Enumeration<String> enumas = ks.aliases();
            String keyAlias = null;
            if ( enumas.hasMoreElements() ) {
                keyAlias = (String) enumas.nextElement();
                System.out.println( "alias=[" + keyAlias + "]" );
            }
            // Now once we know the alias, we could get the keys.
            System.out.println( "is key entry=" + ks.isKeyEntry( keyAlias ) );
            PrivateKey prikey = (PrivateKey) ks.getKey( keyAlias, nPassword );
            Certificate cert = ks.getCertificate( keyAlias );
            PublicKey pubkey = cert.getPublicKey();

            System.out.println( "cert class = " + cert.getClass()
                    .getName() );
            System.out.println( "cert = " + cert );
            System.out.println( "public key = " + pubkey );
            System.out.println( "private key = " + prikey );

            System.out.println( "pub" );
            this.outKey( pubkey );
            System.out.println( "pri" );
            this.outKey( prikey );

            return prikey;
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    private void outKey( Key key ) {
        byte[] keys = key.getEncoded();
        byte[] enc = Base64.getEncoder()
                .encode( keys );
        for ( byte b : enc ) {
            String s = String.format( "0x%2x,", b );
            System.out.print( s );
        }
        System.out.println();
    }

}
