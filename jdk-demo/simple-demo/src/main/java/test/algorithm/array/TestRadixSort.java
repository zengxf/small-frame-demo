package test.algorithm.array;

import java.util.Arrays;

public class TestRadixSort {

    public static void main( String[] args ) {
        int[] arr = { 23, 54, 2, 3, 98, 34, 54, 56, 34, 43, 65, 123, 3 };
        System.out.println( Arrays.toString( arr ) );
        radixSort( arr, 1000 );
        System.out.println( Arrays.toString( arr ) );
    }

    static void radixSort( int[] array, int maxBase ) {
        int base = 1; // 1, 10, 100...
        int length = array.length;
        int[][] bucket = new int[10][length];
        int[] order = new int[10]; //
        while ( base < maxBase ) {
            for ( int num : array ) { //
                int digit = ( num / base ) % 10;
                int index = order[digit]++;
                bucket[digit][index] = num;
            }
            for ( int i = 0, k = 0; i < 10; i++ ) { //
                if ( order[i] != 0 ) {
                    for ( int j = 0; j < order[i]; j++ ) {
                        array[k++] = bucket[i][j];
                    }
                    order[i] = 0;
                }
            }
            base *= 10;
        }
    }

}
