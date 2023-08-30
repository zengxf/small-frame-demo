package test.jdkapi.juc.structure;

import java.util.concurrent.ConcurrentHashMap;

/**
 * key 不能为 null<br>
 * 源码阅读：https://mp.weixin.qq.com/s?__biz=MzUyNjg2ODgyMg==&mid=2247483783&idx=1&sn=5579c3bfd5045df7395b603e8503a58a
 * 
 * <p>
 * Created by zengxf on 2018-03-06
 */
public class TestConcurrentHashMap {

    public static void main( String[] args ) {
        // testCommon();
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.computeIfAbsent( "AaAa", key -> map.computeIfAbsent( "BBBB", key2 -> "value" ) );
        System.out.println( "=================" );
    }

    static void testCommon() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put( "a", 1 );
        map.put( "b", 2 );
        map.put( "d", 4 );
        map.put( "c", 3 );
        System.out.println( map.get( "a" ) );
        System.out.println( map.get( "b" ) );
        System.out.println( map.get( "c" ) );
        System.out.println( map.get( "d" ) );
        System.out.println( map );
        System.out.println( map.size() );
    }

}
