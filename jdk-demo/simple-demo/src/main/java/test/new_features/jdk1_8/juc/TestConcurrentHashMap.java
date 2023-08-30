package test.new_features.jdk1_8.juc;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * Operates with keys and values (forEach, reduce, search) <br>
 * Operates with keys (forEachKey, reduceKeys, searchKeys) <br>
 * Operates with values (forEachValue, reduceValues, searchValues) <br>
 * Operates with Map.Entry objects (forEachEntry, reduceEntries, searchEntries) <br>
 * 
 * <p>
 * Created by zxf on 2017-07-10
 */
public class TestConcurrentHashMap {
    static ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    static {
        map.put( "foo", "bar" );
        map.put( "han", "solo" );
        map.put( "r2", "d2" );
        map.put( "c3", "p0" );
    }

    public static void main( String[] args ) {
        // forEach( );
        // search( );
        // searchValues( );
        // reduce();
        // reduceValuesToInt();
        marge();

        System.out.println( "mappingCount: " + map.mappingCount() );
    }

    static void marge() {
        BiFunction<? super String, ? super String, ? extends String> remappingFunction = ( k, v ) -> null;
        String key = "#non#";
        String value = "";
        map.merge( key, value, remappingFunction );
    }

    // reduceValuesToInt
    // reduceValuesToDouble
    // reduceValuesToLong
    static void reduceValuesToInt() {
        int len = map.reduceValuesToInt( 2, ( value ) -> value.length(), 0, ( i1, i2 ) -> i1 + i2 );
        System.out.println( "len: " + len );
    }

    static void reduce() {
        String result = map.reduce( 1, ( key, value ) -> {
            String v = key + "=" + value;
            System.out.println( "Transform: " + Thread.currentThread()
                    .getName() + "\t" + v );
            return v;
        }, ( s1, s2 ) -> {
            String v = s1 + ", " + s2;
            System.out.println( "Reduce: " + Thread.currentThread()
                    .getName() + "\t" + v );
            return v;
        } );
        System.out.println( "Result: " + result );
    }

    static void searchValues() {
        String result = map.searchValues( 1, value -> {
            System.out.println( Thread.currentThread()
                    .getName() );
            if ( value.length() > 3 ) {
                return value;
            }
            return null;
        } );
        System.out.println( "Result: " + result );
    }

    static void search() {
        String result = map.search( 1, ( key, value ) -> {
            System.out.println( Thread.currentThread()
                    .getName() );
            if ( "foo".equals( key ) ) {
                return value;
            }
            return null;
        } );
        System.out.println( "Result: " + result );
    }

    static void forEach() {
        map.forEach( 1, ( key, value ) -> //
        System.out.printf( "key: %s; value: %s; thread: %s\n", key, value, Thread.currentThread()
                .getName() ) );
    }
}
