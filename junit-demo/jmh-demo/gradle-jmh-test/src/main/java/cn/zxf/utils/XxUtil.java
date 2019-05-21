package cn.zxf.utils;

public class XxUtil {

    public static String uuid() {
        return Long.toString( System.currentTimeMillis() | System.nanoTime(), Character.MAX_RADIX );
    }

}
