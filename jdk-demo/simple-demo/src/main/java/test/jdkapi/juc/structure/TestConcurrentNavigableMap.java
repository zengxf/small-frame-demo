package test.jdkapi.juc.structure;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 排序的，可分段的 Map
 * 
 * <p>
 * Created by zengxf on 2018-04-02
 */
public class TestConcurrentNavigableMap {
    public static void main( String[] args ) {

        ConcurrentNavigableMap<String, String> map = new ConcurrentSkipListMap<String, String>();

        map.put( "3", "Three" );
        map.put( "5", "Five" );
        map.put( "1", "One" );
        map.put( "2", "Two" );
        map.put( "6", "Six" );

        System.out.println( "Initial ConcurrentHashMap: " + map );
        System.out.println( "HeadMap(\"2\") of ConcurrentHashMap: " + map.headMap( "2" ) ); // "2" 前面的元素
        System.out.println( "TailMap(\"2\") of ConcurrentHashMap: " + map.tailMap( "2" ) ); // "2" 后面的元素
        System.out.println( "SubMap(\"2\", \"4\") of ConcurrentHashMap: " + map.subMap( "2", "4" ) ); // "2" 和 "4" 之间的元素

    }
}
