package cn.zxf.speedment_mysql;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.speedment.runtime.core.ApplicationBuilder.LogType;

import cn.zxf.speedment_mysql.test_db.test_schema.user.User;
import cn.zxf.speedment_mysql.test_db.test_schema.user.UserImpl;
import cn.zxf.speedment_mysql.test_db.test_schema.user.UserManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestUser {

    private SpeedmentMysqlApplication app;
    private UserManager               users;

    @Before
    public void init() {
        app = new SpeedmentMysqlApplicationBuilder() //
                .withPassword( "admin" ) //
                .withLogging( LogType.STREAM ) //
                .withLogging( LogType.PERSIST ) //
                .withLogging( LogType.UPDATE ) //
                .withLogging( LogType.REMOVE ) //
                .build();
        users = app.getOrThrow( UserManager.class );
    }

    @After
    public void after() {
        app.stop();
    }

    // -------------

    @Test
    public void test_init() {
        log.info( "users: {}", users );
    }

    @Test
    public void test_count() {
        long count = users.stream().count();
        log.info( "count: {}", count );
    }

    @Test
    public void test_filter_count() {
        long count = users.stream() //
                .filter( User.NAME.startsWith( "zxf" ) ) //
                .count();
        log.info( "filter-count: {}", count );
    }

    @Test
    public void test_find_any() {
        Optional<User> op = users.stream() //
                .filter( User.NAME.startsWith( "zxf" ) ) //
                .findAny();
        op.ifPresent( user -> {
            log.info( "user-class: {}", user.getClass() );
            log.info( "find-any-user: {}", user );
        } );
    }

    @Test
    public void test_insert() {
        User user = new UserImpl();
        user.setName( "test-insert-01" );
        user.setAge( 22 );
        users.persist( user );
        log.info( "insert-user: {}", user );
    }

    @Test
    public void test_update() {
        User user = new UserImpl();
        user.setName( "test-insert-01" );
        user.setAge( 33 * 2 );
        users.update( user );
        log.info( "update-user: {}", user );
    }
    
    @Test
    public void test_remove() {
        User user = new UserImpl();
        user.setName( "test-insert-01" );
        user.setAge( 33 * 2 );
        users.remove( user );
        log.info( "remove-user: {}", user );
    }

    @Test
    public void test_find_all() {
        users.stream().forEach( user -> log.info( "find-all-user: {}", user ) );
    }

}
