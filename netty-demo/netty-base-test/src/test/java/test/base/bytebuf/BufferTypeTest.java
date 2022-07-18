package test.base.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/15.
 */
@Slf4j
public class BufferTypeTest {

    final static Charset UTF_8 = StandardCharsets.UTF_8;

    // 堆缓冲区
    @Test
    public void testHeapBuffer() {
        // 取得堆内存
        ByteBuf heapBuf = ByteBufAllocator.DEFAULT.heapBuffer();
        heapBuf.writeBytes("中文 : test".getBytes(UTF_8));
        PrintAttribute.print("堆内存", heapBuf);
        if (heapBuf.hasArray()) {
            // 取得内部数组
            byte[] array = heapBuf.array();
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
            log.info(new String(array, offset, length, UTF_8));
        }
        heapBuf.release();

    }

    // 直接缓冲区
    @Test
    public void testDirectBuffer() {
        ByteBuf directBuf = ByteBufAllocator.DEFAULT.directBuffer();
        directBuf.writeBytes("中文 : test".getBytes(UTF_8));
        PrintAttribute.print("直接内存", directBuf);
        if (!directBuf.hasArray()) {
            int length = directBuf.readableBytes();
            byte[] array = new byte[length];
            // 读取数据到堆内存
            directBuf.getBytes(directBuf.readerIndex(), array);
            log.info(new String(array, UTF_8));
        }
        directBuf.release();
    }

}