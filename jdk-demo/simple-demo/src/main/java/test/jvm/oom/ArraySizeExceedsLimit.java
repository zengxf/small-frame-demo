package test.jvm.oom;

/**
 * -Xmx200m -Xms200m
 * 
 * <p>
 * Created by zengxf on 2017-11-24
 */
public class ArraySizeExceedsLimit {
    public static void main( String[] args ) {
        ArraySizeExceedsLimit arr = new ArraySizeExceedsLimit();
        arr.arraySizeChecker();
    }

    public void arraySizeChecker() {
        System.out.println( Integer.MAX_VALUE );
        int[] myIntArray = new int[21_4748_3646]; // Integer.MAX_VALUE - 1
        System.out.println( myIntArray.length );
    }
}
