package test.jdkapi.ju;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import lombok.Data;

public class TestComparator {

    public static void main( String[] args ) {
        List<BeanForTestComparator> list = new ArrayList<>();
        list.add( BeanForTestComparator.of( "a", 23 ) );
        list.add( BeanForTestComparator.of( "b", null ) );
        list.add( BeanForTestComparator.of( "c", null ) );
        list.add( BeanForTestComparator.of( "d", 56 ) );
        Function<BeanForTestComparator, Integer> fun = BeanForTestComparator::getValue;
        list.stream() //
                .sorted( ( v1, v2 ) -> {
                    Integer i1 = Optional.ofNullable( v1 ).map( fun ).orElse( null );
                    Integer i2 = Optional.ofNullable( v2 ).map( fun ).orElse( null );
                    if ( i1 == null ) {
                        if ( i2 == null )
                            return 0;
                        return 1;
                    }
                    if ( i2 == null )
                        return -1;
                    return i2.compareTo( i1 );
                } ) //
                .forEach( System.out::println );
    }

    @Data
    static class BeanForTestComparator {
        String  name;
        Integer value;

        static BeanForTestComparator of( String n, Integer v ) {
            BeanForTestComparator obj = new BeanForTestComparator();
            obj.name = n;
            obj.value = v;
            return obj;
        }
    }

}
