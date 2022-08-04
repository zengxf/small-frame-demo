package test.echo_service.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // ping 和 pong 帧已经被前面的 WebSocketServerProtocolHandler 处理器处理过了
        if (frame instanceof TextWebSocketFrame) {
            // 取得请求内容
            String request = ((TextWebSocketFrame) frame).text();
            log.debug("服务端收到：" + request);
            // 回显字符串
            String echo = "服务端 echo：" + request;
            TextWebSocketFrame echoFrame = new TextWebSocketFrame(echo);
            // 发送回显字符串
            ctx.channel().writeAndFlush(echoFrame);
        } else {
            log.debug("本例程仅支持文本消息，不支持二进制消息");
            // 如果不是文本消息，抛出异常
            // 本演示不支持二进制消息
            String message = "unsupported frame type: " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    // 处理用户事件
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 是否握手成功，升级为 Websocket 协议
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            // 握手成功，移除 WebPageHandler，因此将不会接收到任何消息
            ctx.pipeline().remove(WebPageHandler.class);
            log.debug("WebSocket HandshakeComplete 握手成功");
            log.debug("新的 WS 客户端加入，通道为：" + ctx.channel());
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

}