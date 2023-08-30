package test.algorithm.array;

import java.util.Arrays;

/**
 * <p>
 * Created by zengxf on 2018-03-07
 */
public class TestBubbleSort {

    public static void main( String[] args ) {
        int[] arr = { 23, 54, 2, 43, 65, 3 };
        System.out.println( Arrays.toString( arr ) );
        bubbleSort( arr );
        System.out.println( Arrays.toString( arr ) );
    }

    public static void bubbleSort( int[] arr ) {
        int temp = 0;
        for ( int i = arr.length - 1; i > 0; i-- ) { //
            for ( int j = 0; j < i; j++ ) { //
                if ( arr[j] > arr[j + 1] ) { //
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp; //
                }
            }
        }
    }

    public static int[] bubbleSortA( int[] array ) {
        int len = array.length;
        if ( len == 0 )
            return array;
        for ( int i = 0; i < len; i++ )
            for ( int j = 0; j < len - 1 - i; j++ )
                if ( array[j + 1] < array[j] ) {
                    int temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                }
        return array;
    }
}
