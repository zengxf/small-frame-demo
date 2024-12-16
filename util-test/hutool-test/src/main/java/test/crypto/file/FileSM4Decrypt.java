package test.crypto.file;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import test.crypto.Constant;

import java.io.*;

/**
 * 用商密解密文件
 * <p/>
 * ZXF 创建于 2024/12/13
 */
public class FileSM4Decrypt implements Constant {

    static final String
            SRC_FOLDER = "D:/Data/Test/sm4-test/encrypt",
            DE_FOLDER = "D:/Data/Test/sm4-test/decrypt";

    public static void main(String[] args) {
        byte[] myKey = SM4Utils.extractKeys(args);

        File srcFolderFile = new File(SRC_FOLDER);
        FileFilter encryptFilter = f -> f.getName().endsWith(ENCRYPT_SUFFIX);
        File[] childList = srcFolderFile.listFiles(encryptFilter);
        if (childList == null) {
            System.err.println("没有找到加密文件");
            return;
        }

        for (File srcFile : childList) {
            String fileName = srcFile.getName();
            String decryptFileName = fileName.replace(ENCRYPT_SUFFIX, TARGET_SUFFIX);

            System.out.println(fileName);

            try {
                InputStream is = new FileInputStream(srcFile);
                OutputStream os = new FileOutputStream(DE_FOLDER + "/" + decryptFileName);
                SymmetricCrypto sm4 = SmUtil.sm4(myKey);
                sm4.decrypt(is, os, true);  // 加密
            } catch (Exception e) {
                System.err.println(
                        StrUtil.format("解密 [{}] 出错，err: [{}]", fileName, e.getMessage())
                );
            }
        }
    }

}
