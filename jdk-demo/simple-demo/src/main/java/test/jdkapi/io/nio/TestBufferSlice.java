package test.jdkapi.io.nio;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

/**
 * 测试 Buf 切片
 * <p/>
 * Created by ZXFeng on 2024/8/13
 */
@Slf4j
public class TestBufferSlice {

    public static void main(String[] args) {
        byte[] bytes = new byte[]{1, 2, 3, 4, 5};
        log.info("bytes-0: [{}]", bytes);

        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        // 使用 slice 之前，一般先调用 position() 和 limit() 方法，
        byteBuffer.position(2);

        // 2 5 5
        log.info("position: [{}], limit: [{}], capacity: [{}]", byteBuffer.position(), byteBuffer.limit(), byteBuffer.capacity());
        log.info("--------------------------------");

        ByteBuffer sliceBuf = byteBuffer.slice();   // 从位置 2 开始切片
        // 2 5 5
        log.info("position: [{}], limit: [{}], capacity: [{}]", byteBuffer.position(), byteBuffer.limit(), byteBuffer.capacity());
        // 0 3 3
        log.info("position: [{}], limit: [{}], capacity: [{}]", sliceBuf.position(), sliceBuf.limit(), sliceBuf.capacity());

        sliceBuf.put((byte) 6); // 从位置 0 开始填充 (相当于从源数组位置 2 开始填充)
        sliceBuf.put((byte) 7);
        sliceBuf.put((byte) 8);
        // sliceBuf.put((byte) 9);  // 会出错：Buf 溢出 (BufferOverflowException)

        log.info("--------------------------------");
        // 2 5 5
        log.info("position: [{}], limit: [{}], capacity: [{}]", byteBuffer.position(), byteBuffer.limit(), byteBuffer.capacity());
        // 3 3 3
        log.info("position: [{}], limit: [{}], capacity: [{}]", sliceBuf.position(), sliceBuf.limit(), sliceBuf.capacity());

        log.info("bytes-1: [{}]", bytes);

        // 使用 slice() 后，再调用 arrayOffset() 方法时，会出现返回值非 0 的情况
        // 其是对原缓冲区的偏移
        log.info("offset: [{}]", sliceBuf.arrayOffset());
    }

}
