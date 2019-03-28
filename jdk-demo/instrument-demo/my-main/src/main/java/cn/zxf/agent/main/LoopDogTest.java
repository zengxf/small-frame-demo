package cn.zxf.agent.main;

import cn.zxf.agent.test.Dog;

/**
 * 普通启动测试类
 * 
 * <p>
 * Created by zengxf on 2018-02-07
 */
public class LoopDogTest {

    public static void main( String[] args ) throws InterruptedException {
        System.out.println( new Dog().hello() );
        int count = 0;
        while ( true ) {
            Thread.sleep( 500 );
            System.out.println( count++ );
            System.out.println( new Dog().hello() );
        }
    }

}
