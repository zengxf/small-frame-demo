package test.jdkapi.io.file;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ZIP 压缩优化
 * <p>
 * 原文参考：https://www.jianshu.com/p/25b328753017
 * <p>
 * Created by zengxf on 2021-04-28
 */
public class ZipCompressOptimize {

    // 要压缩的图片文件所在所存放位置
    public static String JPG_FILE_PATH = "C:\\Users\\Administrator\\Desktop\\aa\\image\\test\\test-big.jpg";
    // zip压缩包所存放的位置
    public static String ZIP_FILE      = "C:\\Users\\Administrator\\Desktop\\aa\\image\\test\\test-big.zip";
    // 所要压缩的文件
    public static File   JPG_FILE      = null;
    // 文件大小
    public static long   FILE_SIZE     = 0;
    // 文件名
    public static String FILE_NAME     = "";
    // 文件后缀名
    public static String SUFFIX_FILE   = "";

    static {
        File file = new File(JPG_FILE_PATH);
        JPG_FILE = file;
        FILE_NAME = file.getName();
        FILE_SIZE = file.length();
        SUFFIX_FILE = file.getName().substring(file.getName().indexOf('.'));
    }

    public static void main(String[] args) throws Exception {

//        Options opt = new OptionsBuilder()
//                .include(FileCompress.class.getSimpleName())
//                .forks(1).warmupIterations(10).threads(1)
//                .build();
//        new Runner(opt).run();

//        System.out.println("------NoBuffer");
//        zipFileNoBuffer();

//        System.out.println("------Buffer");
//        zipFileBuffer();

        System.out.println("------Channel");
        zipFileChannel();

//        System.out.println("---------Map");
//        zipFileMap();

//        System.out.println("-------Pip");
//        zipFilePip();
        
//        test();
    }

    //Version 1 没有使用Buffer
    public static void zipFileNoBuffer() {
        File zipFile = new File(ZIP_FILE);
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile))) {
            //开始时间
            long beginTime = System.currentTimeMillis();
            byte[] arr = new byte[1024];
            for (int i = 0; i < 1; i++) {
                try (InputStream input = new FileInputStream(JPG_FILE)) {
                    zipOut.putNextEntry(new ZipEntry(FILE_NAME + i));
                    int temp = 0;
//                    while ((temp = input.read()) != -1) { // 这是一次读写一个字节
//                        zipOut.write(temp); 
//                    }
                    while ((temp = input.read(arr)) != -1) { // 这是一次读写 1k
                        zipOut.write(arr, 0, temp);
                    }
                }
            }
            printInfo(beginTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Version 2 使用了Buffer
    public static void zipFileBuffer() {
        //开始时间
        long beginTime = System.currentTimeMillis();
        File zipFile = new File(ZIP_FILE);
        try {
//            ZipOutputStream zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(zipOut);
            for (int i = 0; i < 10; i++) {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(JPG_FILE));
                zipOut.putNextEntry(new ZipEntry(i + SUFFIX_FILE));
                int temp = 0;
                while ((temp = bufferedInputStream.read()) != -1) { // 默认一次读写 8k
                    bufferedOutputStream.write(temp);
                }
                zipOut.closeEntry();
                bufferedInputStream.close();
            }
            zipOut.close();
            printInfo(beginTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void zipFileChannel() {
        //开始时间
        long beginTime = System.currentTimeMillis();
        File zipFile = new File(ZIP_FILE);
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
                WritableByteChannel writableByteChannel = Channels.newChannel(zipOut)) {
            for (int i = 0; i < 10; i++) {
                try (FileChannel fileChannel = new FileInputStream(JPG_FILE).getChannel()) {
                    zipOut.putNextEntry(new ZipEntry(i + SUFFIX_FILE));
                    fileChannel.transferTo(0, FILE_SIZE, writableByteChannel);
                }
            }
            printInfo(beginTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void zipFileMapBuffer() {
        //开始时间
        long beginTime = System.currentTimeMillis();
        File zipFile = new File(ZIP_FILE);
        try (ZipOutputStream zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
                WritableByteChannel writableByteChannel = Channels.newChannel(zipOut)) {
            for (int i = 0; i < 10; i++) {
                zipOut.putNextEntry(new ZipEntry(i + SUFFIX_FILE));

                //内存中的映射文件
                MappedByteBuffer mappedByteBuffer = new RandomAccessFile(JPG_FILE_PATH, "r").getChannel()
                        .map(FileChannel.MapMode.READ_ONLY, 0, FILE_SIZE);

                writableByteChannel.write(mappedByteBuffer);
            }
            printInfo(beginTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Version 4 使用Map映射文件
    public static void zipFileMap() {
        //开始时间
        long beginTime = System.currentTimeMillis();
        File zipFile = new File(ZIP_FILE);
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
                WritableByteChannel writableByteChannel = Channels.newChannel(zipOut)) {
            for (int i = 0; i < 10; i++) {
                zipOut.putNextEntry(new ZipEntry(i + SUFFIX_FILE));

                //内存中的映射文件
                MappedByteBuffer mappedByteBuffer = new RandomAccessFile(JPG_FILE_PATH, "r").getChannel()
                        .map(FileChannel.MapMode.READ_ONLY, 0, FILE_SIZE);

                writableByteChannel.write(mappedByteBuffer);
            }
            printInfo(beginTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //Version 5 使用Pip
//    @Benchmark
    public static void zipFilePip() {

        long beginTime = System.currentTimeMillis();
        try(WritableByteChannel out = Channels.newChannel(new FileOutputStream(ZIP_FILE))) {
            Pipe pipe = Pipe.open();
            //异步任务
            CompletableFuture.runAsync(()->runTask(pipe));

            //获取读通道
            ReadableByteChannel readableByteChannel = pipe.source();
            ByteBuffer buffer = ByteBuffer.allocate(((int) FILE_SIZE)*10);
            while (readableByteChannel.read(buffer)>= 0) {
                buffer.flip();
                out.write(buffer);
                buffer.clear();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        printInfo(beginTime);

    }

    //异步任务
    public static void runTask(Pipe pipe) {

        try(ZipOutputStream zos = new ZipOutputStream(Channels.newOutputStream(pipe.sink()));
                WritableByteChannel out = Channels.newChannel(zos)) {
            System.out.println("Begin");
            for (int i = 0; i < 10; i++) {
                zos.putNextEntry(new ZipEntry(i+SUFFIX_FILE));

                FileChannel jpgChannel = new FileInputStream(new File(JPG_FILE_PATH)).getChannel();

                jpgChannel.transferTo(0, FILE_SIZE, out);

                jpgChannel.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void printInfo( long beginTime ) {
        long timeConsum = ( System.currentTimeMillis() - beginTime ); // 耗时
        System.out.println( "fileSize: " + FILE_SIZE / 1024 + " K" );
        System.out.println( "consum time: " + timeConsum + " ms" );
    }


    //Version 6 使用Pip+Map
//    @Benchmark
    public static void zipFilePipMap() {
        long beginTime = System.currentTimeMillis();
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(ZIP_FILE));
                WritableByteChannel out = Channels.newChannel(fileOutputStream)) {
            Pipe pipe = Pipe.open();
            //异步任务往通道中塞入数据
            CompletableFuture.runAsync(() -> runTaskMap(pipe));
            //读取数据
            ReadableByteChannel workerChannel = pipe.source();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (workerChannel.read(buffer) >= 0) {
                buffer.flip();
                out.write(buffer);
                buffer.clear();
            }
            printInfo(beginTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //异步任务
    public static void runTaskMap(Pipe pipe) {

        try (WritableByteChannel channel = pipe.sink();
                ZipOutputStream zos = new ZipOutputStream(Channels.newOutputStream(channel));
                WritableByteChannel out = Channels.newChannel(zos)) {
            for (int i = 0; i < 10; i++) {
                zos.putNextEntry(new ZipEntry(i + SUFFIX_FILE));

                MappedByteBuffer mappedByteBuffer = new RandomAccessFile(
                        JPG_FILE_PATH, "r").getChannel()
                        .map(FileChannel.MapMode.READ_ONLY, 0, FILE_SIZE);
                out.write(mappedByteBuffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
