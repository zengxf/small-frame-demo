package cn.zxf.redis_mongo.user;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api/user" )
public class UserController {

    @Autowired
    private UserService service;

    /**
     * http://localhost:9090/api/user/find-one/zxf-001
     */
    @GetMapping( "find-one/{id}" )
    public User findOne( @PathVariable String id ) {
        return service.findOne( id );
    }

    /**
     * http://localhost:9090/api/user/find-random
     */
    @GetMapping( "find-random" )
    public User findRandom() {
        String id = "r-" + new Random().nextInt( 1000 );
        return service.findOne( id );
    }

}
