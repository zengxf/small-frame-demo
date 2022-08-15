package utils;

import java.io.IOException;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/11.
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

}
