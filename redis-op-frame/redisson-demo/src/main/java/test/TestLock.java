package test;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

public class TestLock {

    public static void main( String[] args ) {
        RedissonClient redisson = GetRedissonClient.get();

        RLock lock = redisson.getLock( "test-lock" );
        lock.lock();
        System.out.println( "-------------" );
        lock.unlock();

        redisson.shutdown();
    }

}
