package test.crypto.file;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import test.crypto.Constant;

import java.io.*;

/**
 * 使用商密加解密文件
 * <p/>
 * ZXF 创建于 2024/12/13
 */
public class FileSM4Test implements Constant {

    public static void main(String[] args) throws FileNotFoundException {
        String srcFile = "D:/Data/Test/sm4-test/test-01.zip";
        String enFile = "D:/Data/Test/sm4-test/encrypt/test-01.data";
        String deFile = "D:/Data/Test/sm4-test/decrypt/test-01.zip";

        InputStream srcInStream = new FileInputStream(srcFile);
        OutputStream enOutStream = new FileOutputStream(enFile);

        SymmetricCrypto sm4 = SmUtil.sm4(KEY);
        sm4.encrypt(srcInStream, enOutStream, true);    // 加密

        InputStream deInStream = new FileInputStream(enFile);
        OutputStream deOutStream = new FileOutputStream(deFile);

        sm4.decrypt(deInStream, deOutStream, true);     // 解密
    }

}
