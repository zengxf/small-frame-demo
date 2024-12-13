package test.crypto.file;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import test.crypto.Constant;

import java.io.*;
import java.util.stream.Stream;

/**
 * 商密解密文件
 * <p/>
 * ZXF 创建于 2024/12/13
 */
public class FileSM4Decrypt implements Constant {

    public static void main(String[] args) {
        String srcFolder = "D:/Data/Test/sm4-test/encrypt";
        String deFolder = "D:/Data/Test/sm4-test/decrypt";

        File srcFolderFile = new File(srcFolder);
        FileFilter encryptFilter = f -> f.getName().endsWith(ENCRYPT_SUFFIX);
        File[] childList = srcFolderFile.listFiles(encryptFilter);
        if (childList == null) {
            System.err.println("没有找到加密文件");
            return;
        }

        Stream.of(childList)
                .forEach(srcFile -> {
                    String fileName = srcFile.getName();
                    String decryptFileName = fileName.replace(ENCRYPT_SUFFIX, "");

                    System.out.println(fileName);

                    try {
                        InputStream is = new FileInputStream(srcFile);
                        OutputStream os = new FileOutputStream(deFolder + "/" + decryptFileName);
                        SymmetricCrypto sm4 = SmUtil.sm4(KEY);
                        sm4.decrypt(is, os, true);  // 加密
                    } catch (Exception e) {
                        System.err.println(
                                StrUtil.format("解密 [{}] 出错，err: [{}]", fileName, e.getMessage())
                        );
                    }
                });
    }

}
