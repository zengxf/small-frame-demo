package test.base.decoder.int2str;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/19.
 */
public class Integer2StringDecoder extends MessageToMessageDecoder<Integer> {

    @Override
    public void decode(ChannelHandlerContext ctx, Integer msg, List<Object> out) {
        String target = "i => " + msg;
        out.add(target);
    }

}