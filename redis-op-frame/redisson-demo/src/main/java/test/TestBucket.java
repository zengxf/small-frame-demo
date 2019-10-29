package test;

import java.util.Date;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

public class TestBucket {

    public static void main( String[] args ) {
        RedissonClient redisson = GetRedissonClient.get();

        RBucket<User> bucket = redisson.getBucket( "test-bucket" );
        bucket.set( new User( "zxf-01", 31, new Date() ) );

        User obj = bucket.get();
        System.out.println( obj );

        User value = new User( "zxf-02", 32, new Date() );

        boolean sign = bucket.trySet( value );
        System.out.println( sign );

        sign = bucket.compareAndSet( value, new User( "zxf-03", 33, new Date() ) );
        System.out.println( sign );

        obj = bucket.getAndSet( new User( "zxf-04", 34, new Date() ) );
        System.out.println( obj );
        
        bucket.delete();
        
        redisson.shutdown();
    }

}
