package test.new_features.jdk1_4.nio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * <pre>
 * 文件锁定以整个 Java 虚拟机来保持。
 * 但它们不适用于控制同一虚拟机内多个线程对文件的访问。
 * 多个并发线程可安全地使用文件锁定对象。
 * </pre>
 * 
 * <pre>
 * lock()阻塞的方法，锁定范围可以随着文件的增大而增加。
 * 无参lock()默认为独占锁；有参lock(0L, Long.MAX_VALUE, true)为共享锁。
 * tryLock()非阻塞,当未获得锁时,返回null。
 * 无参tryLock()默认为独占锁；有参tryLock(0L, Long.MAX_VALUE, true)为共享锁。
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2018-01-04
 */
public class TestFileLock {

    public static void main( String[] args ) throws IOException, InterruptedException {
        System.out.println( ManagementFactory.getRuntimeMXBean().getName() );
        test();
        Runnable r = () -> {
            try {
                test();
            } catch ( IOException | InterruptedException e ) {
                e.printStackTrace();
            }
        };
        new Thread( r, "test-001" ).start();
    }

    static void test() throws IOException, InterruptedException {
        File file = new File( "d:" + File.separator + "test.txt" );
        FileOutputStream output = null;
        FileChannel fout = null;
        try {
            output = new FileOutputStream( file, true );
            fout = output.getChannel(); // 得到通道
            /**
             * 进行独占锁的操作。 <br>
             * tryLock(): 同一进程，不同线程会等待； 不同进程之间，直接返回 null。<br>
             * lock(): 同一进程，不同线程会等待； 不同进程之间，也会等待。
             */
            FileLock lock = fout.lock();
            System.out.println( "lock: " + lock );
            if ( lock != null ) {
                System.out.println( file.getName() + " 文件锁定" );
                Thread.sleep( 5000 );
                lock.release(); // 释放
                System.out.println( file.getName() + " 文件解除锁定。" );
            } else {
                System.out.println( "未获得文件锁！" );
            }
        } finally {
            if ( fout != null ) {
                fout.close();
            }
            if ( output != null ) {
                output.close();
            }
        }
    }

}
