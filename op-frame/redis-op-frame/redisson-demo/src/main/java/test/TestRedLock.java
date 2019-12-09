package test;

import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

public class TestRedLock {

    public static void main( String[] args ) {
        RedissonClient redisson = GetRedissonClient.get();

        RLock lock1 = redisson.getLock( "test-lock1" );
        RLock lock2 = redisson.getLock( "test-lock2" );
        RLock lock3 = redisson.getLock( "test-lock3" );
        RedissonRedLock lock = new RedissonRedLock( lock1, lock2, lock3 );

        lock.lock();
        System.out.println( "-------------" );
        lock.unlock();

        redisson.shutdown();
    }

}
