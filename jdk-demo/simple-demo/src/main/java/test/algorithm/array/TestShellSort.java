package test.algorithm.array;

import java.util.Arrays;

/**
 *
 * <p>
 * Created by zengxf on 2018-03-07
 */
public class TestShellSort {

    public static void main( String[] args ) {
        int[] arr = { 1, 56, 23, 54, 2, 43, 65 };
        System.out.println( Arrays.toString( arr ) );
        shellSort( arr );
        System.out.println( Arrays.toString( arr ) );
    }

    public static void shellSort( int[] array ) {
        int len = array.length;
        int temp, gap = len / 2;
        while ( gap > 0 ) {
            for ( int i = gap; i < len; i++ ) {
                temp = array[i];
                int preIndex = i - gap;
                while ( preIndex >= 0 && array[preIndex] > temp ) {
                    array[preIndex + gap] = array[preIndex];
                    preIndex -= gap;
                }
                array[preIndex + gap] = temp;
            }
            System.out.println( Arrays.toString( array ) + "\t" + gap );
            gap /= 2;
        }
    }

}
