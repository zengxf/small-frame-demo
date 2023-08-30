package test.jdkapi.ju;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class TestListSort {
    public static void main( String[] args ) {
        List<Integer> list = new ArrayList<>();
        IntStream.rangeClosed( 1, 10 ).forEach( list::add );
        Collections.shuffle( list );
        System.out.println( list );
        Collections.sort( list, Comparator.comparing( Integer::intValue ) );
        System.out.println( list );
        Collections.shuffle( list );
        System.out.println( list );
        Collections.sort( list, Comparator.comparing( Integer::intValue ).reversed() ); // 倒序排
        System.out.println( list );
    }
}
