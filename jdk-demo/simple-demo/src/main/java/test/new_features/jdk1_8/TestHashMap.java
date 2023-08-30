package test.new_features.jdk1_8;

import java.util.HashMap;

/**
 * 解决 hash 冲突：开放地址法和链地址法 <br>
 * Java 用链地址法，简单来说，就是数组加链表的结合
 * <p>
 * 
 * 开放地址法 <br>
 * 开放地址法就是一旦发生冲突，就去寻找下一个空的散列地址
 * 
 * <pre>
 * 线性探测法的公式是： 
 * fi(key)=(f(key)+di) MOD m (di=1，2，3，⋯⋯，m−1) 
 * 二次探测法目的是为了不让关键字都聚集在某一区域，公式为： 
 * fi(key)=(f(key)+di) MOD m (di=1^2，−1^2，2^2，−2^2⋯⋯，q^2，−q^2，q≤m−1)
 * </pre>
 * 
 * 链表长度太长（默认超过8）时，链表就转换为红黑树 <br>
 * 利用红黑树快速增删改查的特点提高HashMap的性能 <br>
 * [参考原文](http://www.importnew.com/20386.html) <br>
 * [Java6 死循环测试](https://blog.csdn.net/xuefeng0707/article/details/40797085)
 * 
 * <p>
 * Created by zengxf on 2018-03-14
 */
public class TestHashMap {
    public static void main( String[] args ) {
        HashMap<A, Object> map = new HashMap<>();
        map.put( new A( "ab" ), 12 );
        map.put( new A( "ac" ), 13 );
        map.put( new A( "bb" ), 22 );
        System.out.println( map );
    }

    static class A {

        final String name;
        final int    hash;

        public A( String name ) {
            super();
            this.name = name;
            this.hash = name.charAt( 0 );
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals( Object obj ) {
            return false;
        }

        @Override
        public String toString() {
            return "A [name=" + name + ", hash=" + hash + "]";
        }

    }
}
