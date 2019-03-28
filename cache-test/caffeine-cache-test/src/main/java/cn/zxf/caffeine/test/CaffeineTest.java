package cn.zxf.caffeine.test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CaffeineTest {

    public static void main( String[] args ) {
        LoadingCache<String, User> cache = Caffeine.newBuilder()
                .maximumSize( 10_000 )
                .expireAfterWrite( 4, TimeUnit.SECONDS )
                .refreshAfterWrite( 1, TimeUnit.SECONDS )
                .build( key -> createUser( key ) );

        User user = cache.get( "zxf-001" );
        log.info( "{}", user );

        // cache.put( "tt", user );

        sleep( 1 );

        user = cache.get( "zxf-001" );
        log.info( "{}", user );

        sleep( 1 );

        user = cache.get( "zxf-001" );
        log.info( "{}", user );

        sleep( 3 );
        user = cache.getIfPresent( "zxf-001" );
        log.info( "{}", user );

        sleep( 5 );
        user = cache.getIfPresent( "zxf-001" );
        log.info( "{}", user );
    }

    static void sleep( long n ) {
        try {
            TimeUnit.SECONDS.sleep( n );
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    }

    static User createUser( String id ) {
        log.info( "create user by id: {}", id );
        return new User().id( id )
                .name( "test-" + id )
                .ldt( LocalDateTime.now() )
                .age( 32 );
    }

    @Data
    @Accessors( fluent = true )
    public static class User {
        private String        id;
        private String        name;
        private int           age;
        private LocalDateTime ldt;
    }

}
