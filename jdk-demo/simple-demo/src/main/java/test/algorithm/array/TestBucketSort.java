package test.algorithm.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Bucket Sort
 * <p>
 * Created by zengxf on 2019-04-08
 */
public class TestBucketSort {

    public static void main( String[] args ) {
        int[] arr = { 23, 54, 2, 3, 43, 65, 123, 3 };
        System.out.println( Arrays.toString( arr ) );
        bucketSort( arr );
        System.out.println( Arrays.toString( arr ) );
    }

    static int indexFor( int a, int min, int step ) {
        return ( a - min ) / step;
    }

    static void bucketSort( int[] arr ) {
        int max = arr[0], min = arr[0];
        for ( int a : arr ) {
            if ( max < a )
                max = a;
            if ( min > a )
                min = a;
        }
        int bucketNum = max / 10 - min / 10 + 1;
        List<List<Integer>> buckList = new ArrayList<>();
        // create bucket
        for ( int i = 1; i <= bucketNum; i++ ) {
            buckList.add( new ArrayList<Integer>() );
        }
        // push into the bucket
        for ( int i = 0; i < arr.length; i++ ) {
            int index = indexFor( arr[i], min, 10 );
            ( (ArrayList<Integer>) buckList.get( index ) ).add( arr[i] );
        }
        int index = 0;
        for ( int i = 0; i < bucketNum; i++ ) {
            List<Integer> bucket = buckList.get( i );
            insertSort( bucket );
            for ( int k : bucket ) {
                arr[index++] = k;
            }
        }
    }

    static void insertSort( List<Integer> bucket ) {
        for ( int i = 1; i < bucket.size(); i++ ) {
            int temp = bucket.get( i );
            int j = i - 1;
            for ( ; j >= 0 && bucket.get( j ) > temp; j-- ) {
                bucket.set( j + 1, bucket.get( j ) );
            }
            bucket.set( j + 1, temp );
        }
    }

}
