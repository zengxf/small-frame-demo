package test;

import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;

public class TestSet {

    public static void main( String[] args ) {
        testSimple();
    }

    static void testSimple() {
        RedissonClient redisson = GetRedissonClient.get();

        RSet<String> set = redisson.getSet( "testset" );
        for ( int i = 0; i < 1000; i++ ) {
            set.add( "test-" + i );
        }
        System.out.println( "size ==> " + set.size() );
        System.out.println( "contains 1 ==> " + set.contains( "test-1" ) );
        System.out.println( "contains 10000 ==> " + set.contains( "test-10000" ) );

        redisson.shutdown();
    }

}
