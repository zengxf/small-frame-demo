package cn.zxf.thread.cpu_schedule;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IOOperation {

    // 数据量
    private static final int            num            = 200_0000;

    // 设置栅栏是为了防止子线程还没结束就执行main线程输出耗时时间
    private static final CountDownLatch countDownLatch = new CountDownLatch( 4 );
    private static ExecutorService      service        = Executors.newFixedThreadPool( 4 );

    private static final String         filePath1      = "C:\\Users\\Administrator\\Desktop\\aa\\test1.txt";
    private static final String         filePath2      = "C:\\Users\\Administrator\\Desktop\\aa\\test2.txt";
    private static final String         filePath3      = "C:\\Users\\Administrator\\Desktop\\aa\\test3.txt";
    private static final String         filePath4      = "C:\\Users\\Administrator\\Desktop\\aa\\test4.txt";
    private static File                 file1          = new File( filePath1 );
    private static File                 file2          = new File( filePath2 );
    private static File                 file3          = new File( filePath3 );
    private static File                 file4          = new File( filePath4 );

    public static void main( String[] args ) throws InterruptedException, IOException {
        // 开始时间
        long startTime = System.currentTimeMillis();

        new Thread( new WriteFileThread( file1 ), "线程1" ).start();
        new Thread( new WriteFileThread( file2 ), "线程2" ).start();
        new Thread( new WriteFileThread( file3 ), "线程3" ).start();
        new Thread( new WriteFileThread( file4 ), "线程4" ).start();

        try {
            countDownLatch.await();
        } finally {
            service.shutdown();
        }

        // 结束时间
        long endTime = System.currentTimeMillis();
        System.out.println();
        System.out.println( "总耗时间为：" + ( endTime - startTime ) / 1000.0 + "s" );

    }

    static class WriteFileThread implements Runnable {
        private File file;

        public WriteFileThread( File file ) {
            this.file = file;
        }

        @Override
        public void run() {
            try {
                writeFile( file );
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }

    static void writeFile( File file ) throws IOException {
        // 判断是否有该文件
        if ( !file.getParentFile()
                .exists() ) {
            file.getParentFile()
                    .mkdirs();
        }
        if ( !file.exists() ) {
            file.createNewFile();
        }
        long startTime = System.currentTimeMillis();
        // 创建输出缓冲流对象
        BufferedWriter bufferedWriter = null;
        bufferedWriter = new BufferedWriter( new FileWriter( file ) );
        for ( int i = 0; i < num; i++ ) {
            bufferedWriter.write( i );
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        long endTime = System.currentTimeMillis();
        System.out.println( Thread.currentThread()
                .getName() + "执行完成，耗时 : " + ( endTime - startTime ) / 1000 + "s" );
        countDownLatch.countDown();
        bufferedWriter.close();
    }

}
