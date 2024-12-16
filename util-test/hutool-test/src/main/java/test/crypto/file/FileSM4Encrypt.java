package test.crypto.file;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import test.crypto.Constant;

import java.io.*;

/**
 * 用商密加密文件
 * <p/>
 * ZXF 创建于 2024/12/13
 */
public class FileSM4Encrypt implements Constant {

    static final String
            SRC_FOLDER = "D:/Data/Test/sm4-test",
            EN_FOLDER = "D:/Data/Test/sm4-test/encrypt";

    public static void main(String[] args) {
        byte[] myKey = SM4Utils.extractKeys(args);

        File srcFolderFile = new File(SRC_FOLDER);
        FileFilter zipFilter = f -> f.getName().endsWith(TARGET_SUFFIX);
        File[] childList = srcFolderFile.listFiles(zipFilter);
        if (childList == null) {
            System.err.println("没有找到 zip 文件");
            return;
        }

        for (File srcFile : childList) {
            String fileName = srcFile.getName();
            String encryptFileName = fileName.replace(TARGET_SUFFIX, ENCRYPT_SUFFIX);

            System.out.println(fileName);

            try {
                InputStream is = new FileInputStream(srcFile);
                OutputStream os = new FileOutputStream(EN_FOLDER + "/" + encryptFileName);
                SymmetricCrypto sm4 = SmUtil.sm4(myKey);
                sm4.encrypt(is, os, true);  // 加密
            } catch (Exception e) {
                System.err.println(
                        StrUtil.format("加密 [{}] 出错，err: [{}]", fileName, e.getMessage())
                );
            }
        }
    }

}
