package cn.zxf.speedment_mysql;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.speedment.runtime.core.ApplicationBuilder.LogType;

import cn.zxf.speedment_mysql.test_db.test_schema.position.Position;
import cn.zxf.speedment_mysql.test_db.test_schema.position.PositionImpl;
import cn.zxf.speedment_mysql.test_db.test_schema.position.PositionManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestPosition {

    private SpeedmentMysqlApplication app;
    private PositionManager           positions;

    @Before
    public void init() {
        app = new SpeedmentMysqlApplicationBuilder() //
                .withPassword( "admin" ) //
                .withLogging( LogType.STREAM ) //
                .withLogging( LogType.PERSIST ) //
                .build();
        positions = app.getOrThrow( PositionManager.class );
    }

    @After
    public void after() {
        app.stop();
    }

    // -------------

    @Test
    public void test_init() {
        log.info( "positions: {}", positions );
    }

    @Test
    public void test_count() {
        long count = positions.stream().count();
        log.info( "count: {}", count );
    }

    @Test
    public void test_filter_count() {
        long count = positions.stream() //
                .filter( Position.NAME.startsWith( "Java" ) ) //
                .count();
        log.info( "filter-count: {}", count );
    }

    @Test
    public void test_find_any() {
        Optional<Position> op = positions.stream() //
                .filter( Position.NAME.startsWith( "Java" ) ) //
                .findAny();
        op.ifPresent( pos -> {
            log.info( "pos-class: {}", pos.getClass() );
            log.info( "find-any-pos: {}", pos );
        } );
    }

    @Test
    public void test_insert() {
        Position pos = new PositionImpl();
        pos.setName( "架构师" );
        pos.setDesc( "年薪60W" );
        positions.persist( pos );
        log.info( "insert-pos: {}", pos );
    }

}
