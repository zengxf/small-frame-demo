package test.jvm.gc.ygc;

import java.util.UUID;

/**
 * <a href="https://www.jianshu.com/p/5524fce8b08f">原文参考</a>
 * <p>
 * 
 * <pre>
 *  java
 *  -XX:StringTableSize=5000000 // 默认 60013，减少 Hash 冲突
 *  -verbose:gc -XX:+PrintGC
 *  -XX:+PrintGCDetails
 *  -XX:+UseConcMarkSweepGC -XX:+UseParNewGC 
 *  -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly 
 *  -XX:+PrintStringTableStatistics 
 *  -Xmx1g -Xms1g -Xmn64m
 *  cn.simple.test.jvm.ygc.StringInternTest
 * </pre>
 * 
 * StringTable 解读
 * 
 * <pre>
 * Number of buckets       :     60013 =    480104 bytes, avg   8.000
 *   bucket 数量默认为 60013，每个 bucket 占用 8字节（如果是 32位 系统，那么每个 bucket 占用 4字节）
 * Number of entries       :   1002557 =  24061368 bytes, avg  24.000
 *   HashTable 的 entry 数量为 1002557（String.intern() 了 100W 个不同的 String 对象）
 * Average bucket size     :    16.706
 *   bucket 中 LinkedList 的平均 size（越大，说明 HashTable 碰撞越严重）
 * Maximum bucket size     :        35
 *   bucket 中 LinkedList 最大的 size
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2018-11-30
 */
public class StringInternTest {

    public static void main( String[] args ) throws Exception {
        int s = 0;
        for ( int i = 0; i < Integer.MAX_VALUE; i++ ) {
            UUID.randomUUID()
                    .toString()
                    .intern();

            if ( i >= 10_0000 && i % 10_0000 == 0 ) {
                System.out.println( "i=" + i );
                s++;
            }

            if ( s == 10 ) { // 50: 500W、25: 250W、10: 100W
                System.out.println();
                System.exit( 0 );
            }
        }
    }

}
