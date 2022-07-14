package test.base.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static test.base.bytebuf.PrintAttribute.print;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/14.
 */
@Slf4j
public class WriteReadTest {

    @Test
    public void testWriteRead() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();

        print("动作：分配 ByteBuf(9, 100)", buffer);

        buffer.writeBytes(new byte[]{1, 2, 3, 4});

        buffer.setBoolean(0, true);
        buffer.setInt(4, 1000);
/*
        buffer.writeBoolean(true); // writerIndex

        buffer.markWriterIndex(); // writerIndex => markedWriterIndex
        buffer.resetWriterIndex(); // writerIndex <= markedWriterIndex


        byte[] dst=  new byte[buffer.readableBytes()] ;

        buffer.readBytes(dst);


        buffer.writeInt();
        buffer.writeIntLE();
        buffer.readLongLE();

       */


        print("动作：写入4个字节 (1,2,3,4)", buffer);

        log.info("start==========:get==========");

        getByteBuf(buffer);
        print("动作：取数据 ByteBuf", buffer);

        log.info("start==========:read==========");
        readByteBuf(buffer);
        print("动作：读完 ByteBuf", buffer);


    }

    //读取一个字节，不改变指针
    private void getByteBuf(ByteBuf buffer) {
        for (int i = 0; i < buffer.readableBytes(); i++) {
            log.info("读取一个字节:" + buffer.getByte(i));// 0,1,2,3

        }
    }

    //读取一个字节
    private void readByteBuf(ByteBuf buffer) {
        while (buffer.isReadable()) {
            log.info("读取一个字节:" + buffer.readByte());
        }
    }


    @Test
    public void testResize() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10, 1024);
        print("动作：分配 ByteBuf(4)", buffer);

        log.info("start==========:写入4个字节==========");
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("动作：写入4个字节 ", buffer);


        log.info("start==========:写入10个字节==========");
        buffer.writeBytes(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11});
        print("动作：写入10个字节 ", buffer);

        log.info("start==========:写入64个字节==========");
        for (int i = 0; i < 64; i++) {
            buffer.writeByte(1);
        }
        print("动作：写入64个字节 ", buffer);


        log.info("start==========:写入128个字节==========");
        for (int i = 0; i < 128; i++) {
            buffer.writeByte(1);
        }
        print("动作：写入128个字节 ", buffer);

    }


}
