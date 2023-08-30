package test.jdkapi.reflex.enums;

import lombok.AllArgsConstructor;
import lombok.ToString;

public class TestEnums {

    public static void main( String[] args ) {
        @SuppressWarnings( "unchecked" )
        Object v = Enum.valueOf( Status.class.asSubclass( Enum.class ), "ACTIVE" );
        System.out.println( v );
    }

    @ToString
    @AllArgsConstructor
    static enum Status {
        ACTIVE( 1), DELETED( 2);

        final int code;
    }

}
