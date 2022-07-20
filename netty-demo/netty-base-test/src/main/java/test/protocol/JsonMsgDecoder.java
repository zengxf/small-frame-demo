package test.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/20.
 */
@Slf4j
public class JsonMsgDecoder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String json = (String) msg;
        JsonMsg jsonMsg = JsonMsg.parseFromJson(json);
        log.info("收到一个 Json 数据包 =》" + jsonMsg);
        ctx.fireChannelRead(jsonMsg);
    }

}
