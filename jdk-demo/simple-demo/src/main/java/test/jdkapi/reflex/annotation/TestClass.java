package test.jdkapi.reflex.annotation;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

@TestB( value = "b", name = "B" )
@MyTarget( "test 1" )
@MyTarget( "test 2" )
public class TestClass {

    public static void main( String[] args ) {
        Annotation[] arr = TestClass.class.getDeclaredAnnotations();
        Stream.of( arr )
                .forEach( System.out::println );
    }

}
