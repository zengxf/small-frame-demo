package cn.zxf.jol.test_basic;

import org.openjdk.jol.datamodel.X86_64_DataModel;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.layouters.HotSpotLayouter;
import org.openjdk.jol.layouters.Layouter;
import org.openjdk.jol.vm.VM;

import cn.zxf.jol.UserVo;
import lombok.extern.slf4j.Slf4j;

/**
 * 只是把实例转换成类计算
 * 
 * <p>
 * Created by zengxf on 2017-09-14
 */
@Slf4j
public class TestClassParseInstance {
    public static void main( String[] args ) {
        log.info( "\n{}", VM.current().details() );

        Layouter l;
        l = new HotSpotLayouter( new X86_64_DataModel() );

        UserVo vo = new UserVo();
        vo.setName( "zxf-001" );
        vo.setCity( "湖南省邵阳市" );

        ClassLayout parseClass = ClassLayout.parseInstance( vo, l );
        log.info( "\n{}", parseClass.toPrintable() );
        log.info( "\n{}", parseClass.toString() );
        log.info( "\nheaderSize: {}\n", parseClass.headerSize() );
        log.info( "\ninstanceSize: {}\n", parseClass.instanceSize() );
    }
}
