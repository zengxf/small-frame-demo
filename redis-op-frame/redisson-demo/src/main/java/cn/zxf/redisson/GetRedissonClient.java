package cn.zxf.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class GetRedissonClient {

    public static Redisson get() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress( "redis://120.25.93.177:27003" )
                .setDatabase( 0 );

        RedissonClient redisson = Redisson.create( config );

        return (Redisson) redisson;
    }

}
