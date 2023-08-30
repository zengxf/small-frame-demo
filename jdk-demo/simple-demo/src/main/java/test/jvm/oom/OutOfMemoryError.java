package test.jvm.oom;

/**
 * 没有足够的内存分配对象
 * 
 * <p>
 * Created by zengxf on 2017-11-24
 */
public class OutOfMemoryError {
    public static void main( String args[] ) {
        OutOfMemoryError ome = new OutOfMemoryError();
        ome.generateMyIntArray( 1, 50 );
    }

    public void generateMyIntArray( int start, int end ) {
        int multiplier = 100;
        for ( int i = start; i < end; i++ ) {
            System.out.println( "Round " + i + " Memory: " + Runtime.getRuntime().freeMemory() );
            int[] myIntList = new int[multiplier];
            for ( int j = i; j > 1; j-- ) {
                myIntList[j] = i;
            }
            multiplier = multiplier * 10;
        }
    }
}
