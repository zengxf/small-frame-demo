package test.base.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/15.
 */
@Slf4j
public class ReferenceTest {
    @Test
    public void testRef() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        log.info("after create:" + buffer.refCnt());

        // buffer.writeInt(0x12345678);

        buffer.retain();
        log.info("after retain:" + buffer.refCnt());

        buffer.release();
        log.info("after release:" + buffer.refCnt());
        buffer.release();
        log.info("after release:" + buffer.refCnt());

        // PrintAttribute.print("ref-test", buffer);

        // 错误: refCnt: 0,不能再 retain
        buffer.retain();
        log.info("after retain:" + buffer.refCnt());
    }
}
