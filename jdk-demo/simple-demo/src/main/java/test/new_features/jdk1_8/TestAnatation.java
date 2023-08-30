package test.new_features.jdk1_8;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TestAnatation {
    public static void main( String[] args ) {
        Hint hint = Person.class.getAnnotation( Hint.class );
        System.out.println( hint ); // null
        Hints hints1 = Person.class.getAnnotation( Hints.class );
        System.out.println( hints1 == null ? "null" : hints1.value().length ); // 2
        Hint[] hints2 = Person.class.getAnnotationsByType( Hint.class );
        System.out.println( hints2.length ); // 2
    }
}

@Retention( RetentionPolicy.RUNTIME )
@interface Hints {
    Hint[] value();
}

@Retention( RetentionPolicy.RUNTIME )
@Repeatable( Hints.class )
@interface Hint {
    String value();
}

// @Hints( { @Hint( "hint1" ), @Hint( "hint2" ) })
@Hint( "hint1" )
@Hint( "hint2" )
class Person {
}