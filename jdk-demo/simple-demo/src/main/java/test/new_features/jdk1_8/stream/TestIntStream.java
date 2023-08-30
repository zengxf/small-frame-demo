package test.new_features.jdk1_8.stream;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestIntStream {
    public static void main( String[] args ) {
        // test_range();
        // test_concat();
        // test_flatMap();
        // test_summarizingInt();
        test_iterate();
    }

    static void test_summarizingInt() {
        IntSummaryStatistics summary = IntStream.range( 1, 8 ) //
                .boxed() //
                .collect( Collectors.summarizingInt( i -> i ) );
        System.out.println( summary );
        System.out.println( "-----" );
    }

    static void test_flatMap() {
        IntStream.rangeClosed( 1, 3 )//
                .boxed() //
                .flatMap( a -> IntStream.rangeClosed( a, 100 ) //
                        .filter( b -> Math.sqrt( a * a + b * b ) % 1 == 0 ) //
                        .mapToObj( b -> {//
                            return new int[] { a, b, a * a + b * b };
                        } ) )//
                .map( Arrays::toString )//
                .forEach( System.out::println );
        System.out.println( "-----" );

        IntStream.rangeClosed( 1, 3 )//
                .boxed() //
                .flatMap( a -> IntStream.rangeClosed( a, 3 ) // 合并流
                        .mapToObj( i -> i ) //
                ) //
                .forEach( System.out::println );
        System.out.println( "-----" );
    }

    static void test_concat() {
        // 1, 1, 2
        IntStream.concat( IntStream.range( 1, 2 ), IntStream.rangeClosed( 1, 2 ) ).forEach( System.out::println );
        System.out.println( "-----" );
    }

    static void test_range() {
        // 1
        IntStream.range( 1, 2 ).forEach( System.out::println );
        System.out.println( "-----" );

        // 1, 2
        IntStream.rangeClosed( 1, 2 ).forEach( System.out::println );
        System.out.println( "-----" );
    }

    static void test_iterate() {
        int[] arr = IntStream.iterate( 0, i -> i + 1 ).limit( 100 ).toArray();
        log.info( "arr: {}", arr );
    }
}
