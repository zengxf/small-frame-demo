package cn.zxf.btrace.common;

public class Sleep {

    public static void sleep( long ms ) {
        try {
            Thread.sleep( ms );
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    }

}
