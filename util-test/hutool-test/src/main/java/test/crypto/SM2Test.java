package test.crypto;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;

import java.security.KeyPair;

// 商密-公钥加密
public class SM2Test {

    public static void main(String[] args) {
        String text = "我是一段测试-1234";

        KeyPair pair = SecureUtil.generateKeyPair("SM2");

        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();

        // 公钥加密，私钥解密
        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);
        String encryptStr = sm2.encryptBase64(text, KeyType.PublicKey);
        String decryptStr = sm2.decryptStr(encryptStr, KeyType.PrivateKey);

        System.out.println(encryptStr);
        System.out.println(decryptStr);
    }

}
