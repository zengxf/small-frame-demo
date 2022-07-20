package test.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/20.
 */
@Slf4j
public class JsonMsgEncoder extends MessageToMessageEncoder<JsonMsg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, JsonMsg user, List<Object> out) {
        // 原始数据
        String json = user.convertToJson();
        log.info("发送报文：" + json);
        out.add(json);
    }

}
