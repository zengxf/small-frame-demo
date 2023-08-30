package test.new_features.jdk1_8.ju;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestMap {
    static Map<String, Integer> map = new HashMap<>();
    static {
        map.put( "a", 11 );
        map.put( "b", 22 );
    }

    public static void main( String[] args ) {
        // test_compute();
        // test_getOrDefault();
        // test_computeIfAbsent();
        // test_computeIfPresent();
        // test_merge();
        // test_putIfAbsent();
        // test_remove_key_value();
        // test_replace();
        // test_replace_old();
        test_replaceAll();
    }

    // 替换所有：根据 key 和旧值替换新值
    static void test_replaceAll() {
        map.replaceAll( ( k, v ) -> {
            log.info( "k: {}, v: {}", k, v );
            return v * 1000;
        } );
        log.info( "map: {}", map );
    }

    // 存在 key，且传入的值与旧值相等时才替换
    static void test_replace_old() {
        String key = "test_non";
        Integer value = 2222, check = 0;
        map.replace( key, check, value );
        log.info( "k: {}, check: {}, v: {}, exists: {}", key, check, map.get( key ), map.containsKey( key ) );

        log.info( "" );
        check = null;
        value = 2222;
        map.replace( key, check, value );
        log.info( "k: {}, check: {}, v: {}, exists: {}", key, check, map.get( key ), map.containsKey( key ) );

        log.info( "" );
        check = null;
        value = 2222;
        map.put( key, null );
        map.replace( key, check, value );
        log.info( "k: {}, check: {}, v: {}, exists: {}", key, check, map.get( key ), map.containsKey( key ) );

        log.info( "" );
        key = "a";
        check = 0;
        value = 2222;
        map.replace( key, check, value );
        log.info( "k: {}, check: {}, v: {}, exists: {}", key, check, map.get( key ), map.containsKey( key ) );

        log.info( "" );
        key = "a";
        check = 11;
        value = 2222;
        map.replace( key, check, value );
        log.info( "k: {}, check: {}, v: {}, exists: {}", key, check, map.get( key ), map.containsKey( key ) );
    }

    // 只要存在 key 就替换，不管值是否为 null
    static void test_replace() {
        String key = "test_non";
        Integer value = 2222;
        map.replace( key, value );
        log.info( "k: {}, v: {}, exists: {}", key, map.get( key ), map.containsKey( key ) );

        log.info( "" );
        map.put( key, null );
        map.replace( key, value );
        log.info( "k: {}, v: {}, exists: {}", key, map.get( key ), map.containsKey( key ) );

        log.info( "" );
        key = "a";
        value = 2222;
        map.replace( key, value );
        log.info( "k: {}, v: {}, exists: {}", key, map.get( key ), map.containsKey( key ) );
    }

    // key 和 value 都对应上才删除
    static void test_remove_key_value() {
        String key = "a";
        Integer value = 2222;
        map.remove( key, value );
        log.info( "k: {}, v: {}, exists: {}", key, map.get( key ), map.containsKey( key ) );

        log.info( "" );
        key = "a";
        value = 11;
        map.remove( key, value );
        log.info( "k: {}, v: {}, exists: {}", key, map.get( key ), map.containsKey( key ) );
    }

    // 不存在或值为 null 则添加
    static void test_putIfAbsent() {
        String key = "test_non";
        Integer value = 2222;
        map.putIfAbsent( key, value );
        log.info( "k: {}, v: {}", key, map.get( key ) );

        log.info( "" );
        key = "a";
        value = 2222;
        map.putIfAbsent( key, value );
        log.info( "k: {}, v: {}", key, map.get( key ) );
    }

    // 没有则直接赋值；有则计算新值，新值不为 null 则赋值，为 null 则删除
    static void test_merge() {
        String key = "test_non";
        Integer value = 2222;
        map.merge( key, value, ( old, incoming ) -> {
            log.info( "old: {}, new: {}", old, incoming );
            return 1111;
        } );
        log.info( "k: {}, v: {}", key, map.get( key ) );

        log.info( "" );
        key = "a";
        value = 2222;
        map.merge( key, value, ( old, incoming ) -> {
            log.info( "old: {}, new: {}", old, incoming );
            return 1111;
        } );
        log.info( "k: {}, v: {}", key, map.get( key ) );

        log.info( "" );
        key = "b";
        value = 2222;
        map.merge( key, value, ( old, incoming ) -> {
            log.info( "old: {}, new: {}", old, incoming );
            return null;
        } );
        log.info( "k: {}, v: {}, exists: {}", key, map.get( key ), map.containsKey( key ) );
    }

    // 如果存在且值不为 null 则设置新值
    static void test_computeIfPresent() {
        String key = "test_non";
        map.computeIfPresent( key, ( k, v ) -> {
            log.info( "k: {}, v: {}", k, v );
            return k.length();
        } );
        log.info( "k: {}, v: {}", key, map.get( key ) );

        key = "test_non1";
        map.put( key, null );
        map.computeIfPresent( key, ( k, v ) -> {
            log.info( "k: {}, v: {}", k, v );
            return k.length();
        } );
        log.info( "k: {}, v: {}", key, map.get( key ) );

        key = "a";
        map.put( key, null );
        map.computeIfPresent( key, ( k, v ) -> {
            log.info( "k: {}, v: {}", k, v );
            return k.length();
        } );
        log.info( "k: {}, v: {}", key, map.get( key ) );
    }

    // 如果不存在或值为 null 则设置新值
    static void test_computeIfAbsent() {
        String key = "test_non";
        map.computeIfAbsent( key, ( k ) -> {
            log.info( "k: {}", k );
            return k.length();
        } );
        log.info( "v: {}", map.get( key ) );

        key = "test_non1";
        map.put( key, null );
        map.computeIfAbsent( key, ( k ) -> {
            log.info( "k: {}", k );
            return k.length();
        } );
        log.info( "k: {}, v: {}", key, map.get( key ) );
    }

    // 有则更新值，没有则添加值
    static void test_compute() {
        String key = "a";
        map.compute( key, ( k, v ) -> {
            log.info( "k: {}, v: {}", k, v );
            return v * 10;
        } );
        log.info( "{}: {}", key, map.get( key ) );

        key = "test_non";
        map.compute( key, ( k, v ) -> {
            log.info( "k: {}, v: {}", k, v );
            return v == null ? 0 : v * 10;
        } );
        log.info( "{}: {}", key, map.get( key ) );
    }

    // 只判断存不存在 key，不判断值是否为 null
    static void test_getOrDefault() {
        Integer v = map.getOrDefault( "test_non", 2 );
        log.info( "v: {}", v );

        map.put( "test_non", null );
        v = map.getOrDefault( "test_non", 2 );
        log.info( "v: {}", v );
    }

}
