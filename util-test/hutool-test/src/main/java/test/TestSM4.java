package test;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

public class TestSM4 {

    public static void main(String[] args) {
        String content = "test中文";
        SymmetricCrypto sm4 = SmUtil.sm4();

        String encryptHex = sm4.encryptHex(content);
        String decryptStr = sm4.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);

        System.out.println(encryptHex);
        System.out.println(decryptStr);
    }

}
