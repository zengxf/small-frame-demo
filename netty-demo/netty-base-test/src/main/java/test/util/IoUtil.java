package test.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/4.
 */
public class IoUtil {

    public static String loadResourceFile(String resourceName) {
        return loadJarFile(IoUtil.class.getClassLoader(), resourceName);
    }

    public static String loadJarFile(ClassLoader loader, String resourceName) {
        InputStream in = loader.getResourceAsStream(resourceName);
        if (null == in) {
            return null;
        }
        String out = null;
        try {
            int len = in.available();
            byte[] bytes = new byte[len];

            int readLength = in.read(bytes);
            if ((long) readLength < len) {
                throw new IOException("File length error：" + len);
            }
            out = new String(bytes, ByteUtil.UTF8);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeQuietly(in);
        }
        return out;
    }

    public static void closeQuietly(java.io.Closeable o) {
        if (null == o) return;
        try {
            o.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
