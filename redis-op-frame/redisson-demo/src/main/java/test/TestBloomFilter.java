package test;

import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;

public class TestBloomFilter {

    public static void main( String[] args ) {
        test();
    }

    static void test() {
        Redisson redisson = GetRedissonClient.get();

        System.out.println( "test -------------" );
        RBloomFilter<String> bf = redisson.getBloomFilter( "test" );
        bf.tryInit( 1000, 0.001 );
        for ( int i = 0; i < 1000; i++ ) {
            bf.add( "test-" + i );
        }
        System.out.println( "contains 1 ==> " + bf.contains( "test-1" ) );
        System.out.println( "contains 10000 ==> " + bf.contains( "test-10000" ) );
        System.out.println( "test -------------" );

        redisson.shutdown();
    }

}
