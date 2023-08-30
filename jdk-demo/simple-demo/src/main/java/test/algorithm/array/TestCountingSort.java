package test.algorithm.array;

import java.util.Arrays;

/**
 * 计数排序
 * <p>
 * Created by zengxf on 2019-04-08
 */
public class TestCountingSort {

    public static void main( String[] args ) {
        int[] arr = { 23, 54, 2, 3, 43, 65, 3 };
        System.out.println( Arrays.toString( arr ) );
        countingSort( arr );
        System.out.println( Arrays.toString( arr ) );
    }

    static void countingSort( int[] array ) {
        int min = array[0], max = array[0];
        for ( int i = 1; i < array.length; i++ ) {
            if ( array[i] > max )
                max = array[i];
            if ( array[i] < min )
                min = array[i];
        }
        int bias = 0 - min;
        byte[] bucket = new byte[max - min + 1];
        for ( int i = 0; i < array.length; i++ ) {
            bucket[array[i] + bias]++;
        }
        int index = 0, i = 0;
        while ( index < array.length ) {
            if ( bucket[i] > 0 ) {
                array[index] = i - bias;
                bucket[i]--;
                index++;
            } else
                i++;
        }
    }

}
