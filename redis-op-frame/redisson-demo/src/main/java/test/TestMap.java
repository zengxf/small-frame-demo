package test;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

public class TestMap {

    public static void main( String[] args ) {
        // testSimple();
        testLock();
    }

    static void testLock() {
        Redisson redisson = GetRedissonClient.get();

        RMap<String, Integer> map = redisson.getMap( "test-map" );
        String key = "k1";
        map.put( key, 33 );
        map.put( key, 44 );
        RLock lock = map.getLock( key );
        lock.lock();
        System.out.println( map.get( key ) );
        lock.unlock();
        map.clear();

        redisson.shutdown();
    }

    static void testSimple() {
        RedissonClient redisson = GetRedissonClient.get();

        RMap<String, Integer> map = redisson.getMap( "test-map" );
        map.put( "k1", 33 );
        map.put( "k2", 44 );
        map.put( "k1", 55 );
        System.out.println( map );
        System.out.println( map.get( "k1" ) );
        System.out.println( map.get( "k2" ) );
        map.clear();

        redisson.shutdown();
    }

}
