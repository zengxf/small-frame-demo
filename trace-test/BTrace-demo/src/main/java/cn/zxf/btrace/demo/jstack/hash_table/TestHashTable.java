package cn.zxf.btrace.demo.jstack.hash_table;

import java.util.Hashtable;
import java.util.Map;

import cn.zxf.btrace.common.Sleep;

public class TestHashTable {

    public static void main( String[] args ) {
        test();
    }

    static void test() {
        while ( true ) {
            put();
            Sleep.sleep( 1000 );
            System.out.println( "--==--" );
        }
    }

    static void put() {
        Map<String, String> map = new Hashtable<>();
        map.put( "a", "a" );
    }

}
