package test;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class GetRedissonClient {

    public static Redisson get() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress( "redis://127.0.0.1:6379" )
                .setConnectionMinimumIdleSize( 2 )
                .setConnectionPoolSize( 2 )
                .setDatabase( 0 );

        RedissonClient redisson = Redisson.create( config );

        return (Redisson) redisson;
    }

}
