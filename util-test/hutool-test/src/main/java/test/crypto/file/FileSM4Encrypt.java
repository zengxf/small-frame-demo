package test.crypto.file;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import test.crypto.Constant;

import java.io.*;
import java.util.stream.Stream;

/**
 * 商密加密文件
 * <p/>
 * ZXF 创建于 2024/12/13
 */
public class FileSM4Encrypt implements Constant {

    public static void main(String[] args) {
        String srcFolder = "D:/Data/Test/sm4-test";
        String enFolder = "D:/Data/Test/sm4-test/encrypt";

        File srcFolderFile = new File(srcFolder);
        FileFilter zipFilter = f -> f.getName().endsWith(".zip");
        File[] childList = srcFolderFile.listFiles(zipFilter);
        if (childList == null) {
            System.out.println("没有找到 zip 文件");
            return;
        }

        Stream.of(childList)
                .forEach(srcFile -> {
                    String fileName = srcFile.getName();
                    String encryptFileName = fileName + ENCRYPT_SUFFIX;

                    System.out.println(srcFile.getName());

                    try {
                        InputStream is = new FileInputStream(srcFile);
                        OutputStream os = new FileOutputStream(enFolder + "/" + encryptFileName);
                        SymmetricCrypto sm4 = SmUtil.sm4(KEY);
                        sm4.encrypt(is, os, true);  // 加密
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

}
