package test.illustration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UndertowBitTest {

    public static void main( String[] args ) throws Exception {
        log.info( "-: [{}]", (int) '-' );
        log.info( "0: [{}]", (int) '0' );
        log.info( "a: [{}]", (int) 'a' );
        log.info( "A: [{}]", (int) 'A' );
        
        log.info( "--bits:   [{}]", Integer.toBinaryString( '-' ) );
        log.info( "0-bits:   [{}]", Integer.toBinaryString( '0' ) );
        log.info( "a-bits:   [{}]", Integer.toBinaryString( 'a' ) );
        log.info( "A-bits:   [{}]", Integer.toBinaryString( 'A' ) );
        log.info( "DF-bits: [{}]", Integer.toBinaryString( 0xDF ) );
        log.info( "FF-bits: [{}]", Integer.toBinaryString( 0xFF ) );
        
        log.info( "a & DF-bits: [{}]", Integer.toBinaryString( 'a' & 0xDF ) );
        log.info( "A & FF-bits: [{}]", Integer.toBinaryString( 'A' & 0xFF ) );

        log.info( "z-bits:   [{}]", Integer.toBinaryString( 'z' ) );
        log.info( "Z-bits:   [{}]", Integer.toBinaryString( 'Z' ) );
    }

}
