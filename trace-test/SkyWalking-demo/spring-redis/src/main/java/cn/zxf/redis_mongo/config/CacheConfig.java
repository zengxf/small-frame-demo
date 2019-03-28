package cn.zxf.redis_mongo.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager( RedisTemplate<?, ?> redisTemplate ) {
        RedisCacheManager manager = new RedisCacheManager( redisTemplate );

        Map<String, Long> expiresMap = new HashMap<>();
        expiresMap.put( "user", 60L ); // 单位秒
        expiresMap.put( "zxf-user", 60L ); // 单位秒
        manager.setExpires( expiresMap );

        return manager;
    }

}
