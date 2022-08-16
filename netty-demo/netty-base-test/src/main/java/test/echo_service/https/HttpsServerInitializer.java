package test.echo_service.https;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;
import test.echo_service.http.HttpEchoHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import static test.util.SSLContextHelper.createServerSSLContext;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/16.
 */
public class HttpsServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 创建服务端 SSL 上下文实例
        SSLContext serverSSLContext = createServerSSLContext();
        // 通过上下文实例，创建服务端的 SSL 引擎
        SSLEngine sslEngine = serverSSLContext.createSSLEngine();
        // 在握手的时候，使用服务端模式
        sslEngine.setUseClientMode(false);
        // 单向认证：在服务端设置不需要验证对端身份，无需客户端证实自己的身份
        sslEngine.setNeedClientAuth(false);
        // 创建 SslHandler 处理器，并加入到流水线
        pipeline.addLast(new SslHandler(sslEngine));
        // 请求解码器和响应编码器
        pipeline.addLast(new HttpServerCodec());
        // HttpObjectAggregator 将 HTTP 消息的多个部分合成一条完整的 HTTP 消息
        // HttpObjectAggregator 用于解析 Http 完整请求
        // 由于 HTTP 解码器会在解析每个 HTTP 消息中生成多个消息对象如 HttpRequest/HttpResponse,HttpContent,LastHttpContent
        // 把多个消息转换为一个单一的完全 FullHttpRequest 或是 FullHttpResponse，
        pipeline.addLast(new HttpObjectAggregator(65535));
        // 自定义的业务 handler，回显 HTTP 请求 URI、请求方法、请求参数
        pipeline.addLast(new HttpEchoHandler());
    }

}
