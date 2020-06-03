package test;

import java.security.KeyPair;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;

public class TestSM2 {

    public static void main( String[] args ) {
        String text = "我是一段测试aaaa";

        KeyPair pair = SecureUtil.generateKeyPair( "SM2" );
        byte[] privateKey = pair.getPrivate()
                .getEncoded();
        byte[] publicKey = pair.getPublic()
                .getEncoded();

        SM2 sm2 = SmUtil.sm2( privateKey, publicKey );
        // 公钥加密，私钥解密
        String encryptStr = sm2.encryptBcd( text, KeyType.PublicKey );
        String decryptStr = StrUtil.utf8Str( sm2.decryptFromBcd( encryptStr, KeyType.PrivateKey ) );

        System.out.println( encryptStr );
        System.out.println( decryptStr );
    }

}
