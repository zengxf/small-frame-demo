package test.util;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_0;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/27.
 */
public class HttpProtocolHelper {

    public static final AttributeKey<HttpVersion>
            PROTOCOL_VERSION_KEY = AttributeKey.valueOf("PROTOCOL_VERSION");
    public static final AttributeKey<Boolean>
            KEEP_ALIVE_KEY = AttributeKey.valueOf("KEEP_ALIVE_KEY");

    private static HttpVersion getHttpVersion(ChannelHandlerContext ctx) {
        if (isHTTP_1_0(ctx))
            return HTTP_1_0;
        else
            return HTTP_1_1;
    }

    public static boolean isHTTP_1_0(ChannelHandlerContext ctx) {
        HttpVersion protocol_version = ctx.channel().attr(PROTOCOL_VERSION_KEY).get();
        if (null == protocol_version)
            return false;
        if (protocol_version.equals(HTTP_1_0))
            return true;
        return false;
    }

    public static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        HttpVersion version = getHttpVersion(ctx);
        FullHttpResponse response = new DefaultFullHttpResponse(
                version, status,
                Unpooled.copiedBuffer("Failure: " + status + "\r\n", CharsetUtil.UTF_8)
        );
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        sendAndCleanupConnection(ctx, response);
    }

    public static void sendAndCleanupConnection(ChannelHandlerContext ctx, FullHttpResponse response) {
        final boolean keepAlive = ctx.channel().attr(KEEP_ALIVE_KEY).get();
        HttpUtil.setContentLength(response, response.content().readableBytes());
        if (!keepAlive) {
            // 如果不是长连接，设置 connection:close 头部
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        } else if (isHTTP_1_0(ctx)) {
            // 如果是 1.0 版本的长连接，设置 connection:keep-alive 头部
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        // 发送内容
        ChannelFuture writePromise = ctx.writeAndFlush(response);
        if (!keepAlive) {
            // 如果不是长连接，发送完成之后，关闭连接
            writePromise.addListener(ChannelFutureListener.CLOSE);
        }
    }

    public static void cacheHttpProtocol(ChannelHandlerContext ctx, final FullHttpRequest request) {
        // 每一个连接设置一次即可，不需要重复设置
        if (ctx.channel().attr(KEEP_ALIVE_KEY).get() == null) {
            ctx.channel().attr(PROTOCOL_VERSION_KEY).set(request.protocolVersion());
            final boolean keepAlive = HttpUtil.isKeepAlive(request);
            ctx.channel().attr(KEEP_ALIVE_KEY).set(keepAlive);
        }
    }

    public static void sendJsonContent(ChannelHandlerContext ctx, String content) {
        HttpVersion version = getHttpVersion(ctx);
        // 构造一个默认的 FullHttpResponse 实例
        FullHttpResponse response = new DefaultFullHttpResponse(
                version, OK,
                Unpooled.copiedBuffer(content, CharsetUtil.UTF_8)
        );
        // 设置响应头
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
        // 发送响应内容
        sendAndCleanupConnection(ctx, response);
    }

}
