package test.jdkapi.serial;

import lombok.extern.slf4j.Slf4j;
import util.SerialUtils;

@Slf4j
public class TestSerialMain {

    public static void main( String[] args ) {
        // String path = "L:/tmp/java/serial/dto.bin";
        // testWrite( path );
        // testRead( path );
        String path1 = "L:/tmp/java/serial/dtob.bin";
        testWriteDto( path1 );
        testReadDto( path1 );
    }

    static void testReadDto( String path ) {
        TestADto dto = SerialUtils.readObject( path );
        log.info( "read: {}", dto );
    }

    static void testWriteDto( String path ) {
        TestADto dto = new TestADto();
        TestBDto dtoB = new TestBDto();
        dtoB.setName( "zxf" );
        dtoB.setMsg( "zxf test" );
        dto.setDto( dtoB );
        SerialUtils.writeObject( dto, path );
        log.info( "write: {}", dto );
    }

    static void testRead( String path ) {
        SerialDto dto = SerialUtils.readObject( path );
        System.out.println( dto );
        System.out.println( SerialDto.sign );
    }

    static void testWrite( String path ) {
        SerialDto dto = new SerialDto();
        SerialUtils.writeObject( dto, path );
        System.out.println( dto );
    }

}
