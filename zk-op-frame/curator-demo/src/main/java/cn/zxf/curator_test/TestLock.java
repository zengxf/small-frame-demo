package cn.zxf.curator_test;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * <a href="https://juejin.im/post/5c01532ef265da61362232ed">加锁原理</a>
 * <p>
 * Created by zengxf on 2018-12-14
 */
public class TestLock {

    public static void main( String[] args ) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry( 1000, 2 );
        CuratorFramework client = CuratorFrameworkFactory.newClient( "localhost:2181", retryPolicy );
        client.start();

        // client.create()
        // .forPath( "/zxf", "test".getBytes() );
        // client.create()
        // .forPath( "/zxf/test_data", "test".getBytes() );

        System.out.println( "进入 - " + LocalTime.now() );
        InterProcessMutex lock = new InterProcessMutex( client, "/zxf2/test_lock" );
        if ( lock.acquire( 1000, TimeUnit.MILLISECONDS ) ) {
            try {
                System.out.println( "获锁成功 - " + LocalTime.now() );
            } finally {
                lock.release();
            }
        } else {
            System.out.println( "获锁失败 - " + LocalTime.now() );
        }
        System.out.println( "退出 - " + LocalTime.now() );
    }

}
