package test.base.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static test.base.bytebuf.PrintAttribute.print;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/18.
 */
@Slf4j
public class SliceTest {

    @Test
    public void testSlice() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        print("动作：分配 ByteBuf(9, 100)", buffer);

        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("动作：写入4个字节 (1,2,3,4)", buffer);

        ByteBuf slice = buffer.slice();
        print("动作：切片 slice", slice);

        byte[] dst = new byte[4];
        slice.readBytes(dst);
        log.info("dst = {}", dst);
        print("动作：读取之后 slice", slice);  // 读完，为空
        print("动作：读取之后 buffer", buffer);

        buffer.readByte();
        print("动作：切片 slice1 之前 buffer", buffer);

        ByteBuf slice1 = buffer.slice();
        print("动作：切片 slice1", slice1);

        buffer.retain();
        log.info("4.0 refCnt(): " + buffer.refCnt());
        log.info("4.0 slice refCnt(): " + slice.refCnt());
        log.info("4.0 slice1 refCnt(): " + slice1.refCnt());
        buffer.release();
        log.info("4.0 refCnt(): " + buffer.refCnt());
        log.info("4.0 slice refCnt(): " + slice.refCnt());
        log.info("4.0 slice1 refCnt(): " + slice1.refCnt());
    }

}
