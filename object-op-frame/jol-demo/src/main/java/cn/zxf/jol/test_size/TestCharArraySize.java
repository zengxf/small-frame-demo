package cn.zxf.jol.test_size;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestCharArraySize {
    public static void main( String[] args ) {
        char[] arr = { 'a', 'b', 'c', 'd', 'e' };

        log.info( "arr: {}", arr );
        log.info( "\n-----------------\n-----------------\n-----------------" );
        
        ClassLayout parseClass = ClassLayout.parseInstance( arr );
        log.info( "\n{}", parseClass.toPrintable() );
        log.info( "\n{}", parseClass.toString() );
        log.info( "\nheaderSize: {}\n", parseClass.headerSize() );
        log.info( "\ninstanceSize: {}\n", parseClass.instanceSize() );
        log.info( "\n-----------------\n-----------------\n-----------------" );

        GraphLayout parse = GraphLayout.parseInstance( arr );
        log.info( "\ntoPrintable: \n{}", parse.toPrintable() );
        log.info( "\ntoFootprint: \n{}", parse.toFootprint() );
        log.info( "\ntotalCount: {}", parse.totalCount() );
        log.info( "\ntotalSize: {}", parse.totalSize() );
    }
}
