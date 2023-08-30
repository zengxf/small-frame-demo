package test.jdkapi.juc.structure;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 基于跳表实现的 Map
 * <p>
 * 支持更高的并发。存取时间是 log(N)，和线程数几乎无关。也就是说，并发的线程越多，其越能体现出优势。
 * <p>
 * key 不能为 null，如果没传比较器 Comparator，则 key 要实现 Comparable 接口
 * 
 * <p>
 * Created by zengxf on 2018-03-06
 */
public class TestConcurrentSkipListMap {

    public static void main( String[] args ) {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
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
