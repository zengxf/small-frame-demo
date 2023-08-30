package test.new_features.jdk1_8.ju;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Map.keySet().forEach() 循环问题
 * 
 * <p>
 * Created by zengxf on 2018-04-20
 */
public class TestMapLoop {

    public static void main( String[] args ) {
        Map<String, String> map = data();
        // error( map );
        correct( map );
    }

    static void correct( Map<String, String> map ) {
        map.entrySet()
                .stream()
                .collect( Collectors.toMap( e -> {
                    String newKey = e.getKey();
                    String[] arr = newKey.split( "\\." );
                    return arr[1];
                }, Entry::getValue ) )
                .forEach( map::put );
        System.out.println( map );
    }

    static void error( Map<String, String> map ) {
        map.keySet()
                .forEach( key -> {
                    String newKey = key.toString();
                    String[] arr = newKey.split( "\\." );
                    if ( arr.length > 1 )
                        newKey = arr[1];
                    map.put( newKey, map.get( key ) );
                } );
        System.out.println( map );
    }

    static Map<String, String> data() {
        Map<String, String> map = new HashMap<>();
        map.put( "user.name", "zxf" );
        map.put( "user.age", "23" );
        return map;
    }

}
