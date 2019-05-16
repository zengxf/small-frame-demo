package cn.zxf.jol.test_basic;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

import cn.zxf.jol.UserVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestBasicMain {
    public static void main( String[] args ) {
        log.info( "\n{}", VM.current().details() );

        ClassLayout parseClass = ClassLayout.parseClass( UserVo.class );
        log.info( "\n{}", parseClass.toPrintable() );
        log.info( "\n{}", parseClass.toString() );
        log.info( "\nheaderSize: {}\n", parseClass.headerSize() );
        log.info( "\ninstanceSize: {}\n", parseClass.instanceSize() );

        UserVo vo = new UserVo();
        vo.setName( "zxf-001" );
        GraphLayout parse = GraphLayout.parseInstance( vo );
        log.info( "\n{}", parse.toPrintable() );
        log.info( "\n{}", parse.toFootprint() );
        log.info( "\ntotalCount: {}", parse.totalCount() );
        log.info( "\ntotalSize: {}", parse.totalSize() );
    }
}
