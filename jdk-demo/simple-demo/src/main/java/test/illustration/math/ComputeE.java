package test.illustration.math;

import test.illustration.util.Factorial;

/**
 * 计算自然数 <br>
 * <a href="https://www.zhihu.com/question/36558928">原文参考</a>
 * <p>
 * Created by zengxf on 2018-12-20
 */
public class ComputeE {

    static int n = 12; // 等于 13 时就大了

    public static void main( String[] args ) {
        double e = 0;
        for ( int i = 0; i <= n; i++ ) {
            e += ( 1D / Factorial.factorial( i ) );
        }
        System.out.println( Math.E );
        System.out.println( e );
    }

}
