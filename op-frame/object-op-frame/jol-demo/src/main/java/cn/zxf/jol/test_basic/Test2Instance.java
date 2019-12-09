package cn.zxf.jol.test_basic;

import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

import cn.zxf.jol.UserVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test2Instance {
    public static void main( String[] args ) {
        log.info( "\n{}", VM.current().details() );

        UserVo vo = new UserVo();
        vo.setName( "zxf-001" );
        UserVo vo1 = new UserVo();
        vo1.setName( "zxf-001-0001" );

        GraphLayout parse = GraphLayout.parseInstance( vo, vo1 );
        log.info( "\n{}", parse.toPrintable() );
        log.info( "\n{}", parse.toFootprint() );
        log.info( "\ntotalCount: {}", parse.totalCount() );
        log.info( "\ntotalSize: {}", parse.totalSize() );
    }
}
