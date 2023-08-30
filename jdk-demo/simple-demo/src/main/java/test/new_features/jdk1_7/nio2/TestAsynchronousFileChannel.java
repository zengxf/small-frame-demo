package test.new_features.jdk1_7.nio2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class TestAsynchronousFileChannel {
    public static void main( String[] args ) throws IOException, URISyntaxException {
        // readByFuture();
        // readByCompletionHandler();
        // writeByFuture();
        writeByCompletionHandler();
    }

    static void writeByCompletionHandler() throws URISyntaxException, IOException {
        URL src = TestAsynchronousFileChannel.class.getResource( "_test-write.txt" );
        System.out.println( src );

        Path path = Paths.get( src.toURI() );
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open( path, StandardOpenOption.WRITE );

        ByteBuffer buffer = ByteBuffer.allocate( 1024 );
        long position = 0;

        buffer.put( "test data".getBytes() );
        buffer.flip();

        fileChannel.write( buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {

            @Override
            public void completed( Integer result, ByteBuffer attachment ) {
                System.out.println( "bytes written: " + result );
            }

            @Override
            public void failed( Throwable exc, ByteBuffer attachment ) {
                System.out.println( "Write failed" );
                exc.printStackTrace();
            }
        } );
    }

    static void writeByFuture() throws URISyntaxException, IOException {
        URL src = TestAsynchronousFileChannel.class.getResource( "_test-write.txt" );
        System.out.println( src );

        Path path = Paths.get( src.toURI() );
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open( path, StandardOpenOption.WRITE );

        ByteBuffer buffer = ByteBuffer.allocate( 1024 );
        long position = 0;

        buffer.put( "test data".getBytes() );
        buffer.flip();

        Future<Integer> operation = fileChannel.write( buffer, position );
        buffer.clear();

        while ( !operation.isDone() )
            ;

        System.out.println( "Write done" );
    }

    static void readByCompletionHandler() throws URISyntaxException, IOException {
        ByteBuffer buffer = ByteBuffer.allocate( 1024 );
        long position = 0;

        URL src = TestAsynchronousFileChannel.class.getResource( "_test-read.txt" );
        System.out.println( src );

        Path path = Paths.get( src.toURI() );
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open( path, StandardOpenOption.READ );
        fileChannel.read( buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed( Integer result, ByteBuffer attachment ) {
                System.out.println( "result = " + result );

                attachment.flip();
                byte[] data = new byte[attachment.limit()];
                attachment.get( data );
                System.out.println( new String( data ) );
                attachment.clear();
            }

            @Override
            public void failed( Throwable exc, ByteBuffer attachment ) {

            }
        } );
    }

    static void readByFuture() throws URISyntaxException, IOException {
        URL src = TestAsynchronousFileChannel.class.getResource( "_test-read.txt" );
        System.out.println( src );

        Path path = Paths.get( src.toURI() );
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open( path, StandardOpenOption.READ );

        ByteBuffer buffer = ByteBuffer.allocate( 1024 );
        long position = 0;

        Future<Integer> operation = fileChannel.read( buffer, position );

        while ( !operation.isDone() )
            ;

        buffer.flip();
        byte[] data = new byte[buffer.limit()];
        buffer.get( data );
        System.out.println( new String( data ) );
        buffer.clear();
    }
}
