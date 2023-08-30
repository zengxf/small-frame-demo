package util;

import java.io.*;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/15.
 */
public class IoUtils {

    public static void closeQuietly(java.io.Closeable o) {
        if (null == o) return;
        try {
            o.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String byteToHex(byte[] byteArray) {
        StringBuffer strBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                strBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            } else {
                strBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return strBuff.toString();
    }

    public static byte[] readFileByBytes(String fileName) {
        File file = new File(fileName);
        InputStream in = null;
        byte[] txt = new byte[(int) file.length()];
        try {
            // 一次读一个字节
            in = new FileInputStream(file);
            int tempByte;
            int i = 0;
            while ((tempByte = in.read()) != -1) {
                txt[i] = (byte) tempByte;
                i++;
            }
            in.close();
            return txt;
        } catch (IOException e) {
            e.printStackTrace();
            return txt;
        }
    }

    public static void saveFile(byte[] bfile, String filePath) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            //判断文件目录是否存在
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(filePath);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

}
