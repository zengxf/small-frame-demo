package cn.zxf.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisStringCommands;

public class TestSimple {

    public static void main( String[] args ) {
        RedisClient client = RedisClient.create( "redis://120.25.93.177:27003" );
        StatefulRedisConnection<String, String> connection = client.connect();
        RedisStringCommands<String, String> redis = connection.sync();

        String key = "zxf-test";
        // redis.append( key, "test" );
        redis.set( key, "test-1", new SetArgs().ex( 1000 ) );

        String value = redis.get( key );
        System.out.println( value );
    }

}
