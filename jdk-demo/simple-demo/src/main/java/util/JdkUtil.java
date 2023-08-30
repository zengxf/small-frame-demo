package util;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * <br/>
 * Created by ZXFeng on 2023/2/12.
 */
public class JdkUtil {

    public static Unsafe getUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

}
