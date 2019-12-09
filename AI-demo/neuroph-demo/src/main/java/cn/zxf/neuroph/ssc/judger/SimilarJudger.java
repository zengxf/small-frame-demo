package cn.zxf.neuroph.ssc.judger;

import cn.zxf.neuroph.ssc.ResultJudger;

/**
 * 四舍五入近似判断
 * 
 * <p>
 * Created by zengxf on 2017-11-21
 */
public class SimilarJudger implements ResultJudger {

    public static SimilarJudger INSTANCE = new SimilarJudger();

    @Override
    public boolean correct( double[] result, double[] desired ) {
        if ( result.length != desired.length )
            throw new RuntimeException( "数组长度不一致！result: " + result.length + ", desired: " + desired.length );
        for ( int i = 0; i < result.length; i++ ) {
            if ( Math.round( result[i] ) != desired[i] )
                return false;
        }
        return true;
    }

}
