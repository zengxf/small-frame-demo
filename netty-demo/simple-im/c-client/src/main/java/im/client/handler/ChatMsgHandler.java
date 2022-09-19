package im.client.handler;

import im.common.dto.msg.ProtoMsg;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/19.
 */
@ChannelHandler.Sharable
@Service
public class ChatMsgHandler extends ChannelInboundHandlerAdapter {

    /*** 业务逻辑处理 */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 判断消息实例
        if (msg == null || !(msg instanceof ProtoMsg.Message)) {
            super.channelRead(ctx, msg);
            return;
        }
        // 判断类型
        ProtoMsg.Message pkg = (ProtoMsg.Message) msg;
        ProtoMsg.HeadType headType = pkg.getType();
        if (!headType.equals(ProtoMsg.HeadType.MESSAGE_REQUEST)) {
            super.channelRead(ctx, msg);
            return;
        }
        ProtoMsg.MessageRequest req = pkg.getMessageRequest();
        String content = req.getContent();
        String uid = req.getFrom();
        System.out.println("收到消息 from uid: " + uid + " -> " + content);
    }

}
