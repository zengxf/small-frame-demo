package cn.zxf.jol.test_basic;

import org.openjdk.jol.datamodel.X86_32_DataModel;
import org.openjdk.jol.datamodel.X86_64_COOPS_DataModel;
import org.openjdk.jol.datamodel.X86_64_DataModel;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.layouters.CurrentLayouter;
import org.openjdk.jol.layouters.HotSpotLayouter;
import org.openjdk.jol.layouters.Layouter;

import cn.zxf.jol.UserVo;

public class TestDataModels {
    public static void main( String[] args ) {
        Layouter l;

        l = new CurrentLayouter();
        System.out.println( "***** " + l );
        System.out.println( ClassLayout.parseClass( UserVo.class, l ).toPrintable() );

        l = new HotSpotLayouter( new X86_32_DataModel() );
        System.out.println( "***** " + l );
        System.out.println( ClassLayout.parseClass( UserVo.class, l ).toPrintable() );

        l = new HotSpotLayouter( new X86_64_DataModel() );
        System.out.println( "***** " + l );
        System.out.println( ClassLayout.parseClass( UserVo.class, l ).toPrintable() );

        l = new HotSpotLayouter( new X86_64_COOPS_DataModel() );
        System.out.println( "***** " + l );
        System.out.println( ClassLayout.parseClass( UserVo.class, l ).toPrintable() );
    }
}
