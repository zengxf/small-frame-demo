package test.jdkapi.juc.structure;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 写的时候(add, set)：加锁->创建新数组->复制->设置元素->设置回字段，效率比较低<br>
 * 读的时候直接返回数组元素
 * 
 * <p>
 * Created by zengxf on 2018-03-05
 */
public class TestCopyOnWriteArrayList {

    public static void main( String[] args ) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add( "a" );
        list.add( "a" );
        list.add( "a" );
        list.get( 0 );
        System.out.println( list.size() );
    }

}
