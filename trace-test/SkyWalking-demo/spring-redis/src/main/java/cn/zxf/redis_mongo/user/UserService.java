package cn.zxf.redis_mongo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserService {

    @Autowired
    private MongoTemplate mongo;

    @Cacheable( value = "user", key = "'user-' + #id" )
    public User findOne( String id ) {
        User user = User.builder()
                .id( id )
                .name( "test-" + id )
                .status( 1 )
                .build();
        log.info( "findOne - user: {}", user );

        mongo.save( user );

        return user;
    }

}
