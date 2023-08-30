package test.jdkapi.io.nio;

import java.nio.ByteBuffer;

public class TestDirectBuffer {

    public static void main( String[] args ) {
        ByteBuffer buf = ByteBuffer.allocateDirect( 1000 );
        System.out.println( buf );
    }

}
