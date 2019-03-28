package cn.zxf.neuroph.ssc.common.data;

import java.util.stream.DoubleStream;

import lombok.Getter;
import lombok.ToString;

@ToString
public class CodeSplitVo {

    @Getter
    public String   date;
    @Getter
    public int      period;
    public double[] data;

    public static CodeSplitVo of( String date, int period, double... arr ) {
        CodeSplitVo vo = new CodeSplitVo();
        vo.date = date;
        vo.period = period;
        vo.data = arr;
        return vo;
    }

    /**
     * 后两位
     */
    public double[] output() {
        return this.output( 3 );
    }

    public double[] output( int skip ) {
        return DoubleStream.of( data ).skip( skip ).toArray();
    }

}
