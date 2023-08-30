package test.jdkapi.juc.atomic;

import java.util.concurrent.atomic.LongAdder;

/**
 * 加法器：分段加减操作，适用于高并发 <br>
 * 根据线程探针获取 Cell，线程探针：等于随机数一常量的（值：0x9e3779b9）累加 <br>
 * 源码解析：https://mp.weixin.qq.com/s?__biz=MzUyNjg2ODgyMg==&mid=2247483777&idx=1&sn=f13c488aa3fe20c30fd04cc3c11d7c4d
 * 
 * <p>
 * Created by zengxf on 2018-03-06
 */
public class TestLongAdder {

    public static void main( String[] args ) {
        LongAdder num = new LongAdder();
        num.add( 2 );
        num.add( 2 );
        num.increment();
        System.out.println( num.sum() );
        num.decrement();
        System.out.println( num.sum() );
        num.reset();
        System.out.println( num.longValue() );
    }

}
