package cn.zxf.neuroph.ssc.judger;

import java.util.Arrays;

import cn.zxf.neuroph.ssc.ResultJudger;

/**
 * 完全相等判断
 * 
 * <p>
 * Created by zengxf on 2017-11-21
 */
public class EqualJudger implements ResultJudger {

    public static EqualJudger INSTANCE = new EqualJudger();

    @Override
    public boolean correct( double[] result, double[] desired ) {
        if ( result.length != desired.length )
            throw new RuntimeException( "数组长度不一致！result: " + result.length + ", desired: " + desired.length );
        return Arrays.equals( result, desired );
    }

}
