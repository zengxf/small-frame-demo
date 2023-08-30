package test.jdkapi.ju;

import java.util.HashMap;
import java.util.IdentityHashMap;

/**
 * IdentityHashMap 判断引用是否相等，HashMap 是判断对象是否相等
 * 
 * <p>
 * Created by zengxf on 2018-03-21
 */
public class TestIdentityHashMap {

    public static void main( String[] args ) {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        map.put( new String( "a" ), "aaa" );
        map.put( new String( "a" ), "bbb" );
        System.out.println( map );

        System.out.println( "------------" );

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put( new String( "a" ), "aaa" );
        hashMap.put( new String( "a" ), "bbb" );
        System.out.println( hashMap );
    }

}
