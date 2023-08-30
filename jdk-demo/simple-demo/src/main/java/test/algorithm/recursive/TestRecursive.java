package test.algorithm.recursive;

import java.util.stream.LongStream;

// -Xss68K
public class TestRecursive {
    public static void main( String[] args ) throws InterruptedException {
        long result = 0;
        long n = 800;
        result = factorialTailRecursive( n );
        // result = factorialRecursive( n );
        result = factorialStreams( n );
        result = factorialIterative( n );
        System.out.println( result );
    }

    // 尾递归 - java 没有优化，没卵用
    static long factorialTailRecursive( long n ) {
        return factorialHelper( 1, n );
    }

    static long factorialHelper( long acc, long n ) {
        return n == 1 ? acc : factorialHelper( acc * n, n - 1 );
    }

    // 传统递归
    static long factorialRecursive( long n ) {
        return n == 1 ? 1 : n * factorialRecursive( n - 1 );
    }

    // java8 流
    static long factorialStreams( long n ) {
        return LongStream.rangeClosed( 1, n )
                .reduce( 1, ( long a, long b ) -> a * b );
    }

    // 迭代
    static long factorialIterative( long n ) {
        long r = 1;
        for ( long i = 1; i <= n; i++ ) {
            r *= i;
        }
        return r;
    }
}
