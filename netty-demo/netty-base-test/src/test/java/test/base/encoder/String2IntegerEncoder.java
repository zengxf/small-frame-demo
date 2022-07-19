package test.base.encoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/19.
 */
@Slf4j
public class String2IntegerEncoder extends MessageToMessageEncoder<String> {
    @Override
    protected void encode(ChannelHandlerContext c, String s, List<Object> list) {
        log.info(s);
        char[] array = s.toCharArray();
        for (char a : array) {
            // 48 是 0 的编码，57 是 9 的编码
            if (a >= 48 && a <= 57) {
                list.add(Integer.valueOf("" + a));
            }
        }
    }
}