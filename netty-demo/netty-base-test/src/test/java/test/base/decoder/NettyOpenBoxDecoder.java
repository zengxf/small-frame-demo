package test.base.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import test.base.decoder.byte2str.StringProcessHandler;
import test.util.ByteUtil;
import test.util.ThreadUtil;

import java.io.UnsupportedEncodingException;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/19.
 */
@Slf4j
public class NettyOpenBoxDecoder {

    public static final int MAGIC_CODE = 56869;
    public static final int VERSION = 100;
    static final String SPLIT_RN = "\r\n";
    static final String SPLIT_N = "\n";
    static final String SPLIT_T = "\t";
    static final String CONTENT = " 中 234 ";

    @Test
    public void testLineBasedFrameDecoder() throws UnsupportedEncodingException {
        ChannelInitializer<EmbeddedChannel> i = new ChannelInitializer<>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        for (int j = 0; j < 3; j++) {
            int num = 3;
            ByteBuf buf = Unpooled.buffer();
            for (int k = 0; k < num; k++) {
                buf.writeBytes(CONTENT.getBytes("UTF-8"));
            }
            buf.writeBytes(SPLIT_RN.getBytes("UTF-8"));
            channel.writeInbound(buf);
        }
        ThreadUtil.sleep5s();
    }

    @Test
    public void testDelimiterBasedFrameDecoder() throws UnsupportedEncodingException {
        final ByteBuf delimiter = Unpooled.copiedBuffer(SPLIT_T.getBytes("UTF-8"));
        ChannelInitializer<EmbeddedChannel> i = new ChannelInitializer<>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, true, delimiter));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        for (int j = 0; j < 3; j++) {
            int num = 3;
            ByteBuf buf = Unpooled.buffer();
            for (int k = 0; k < num; k++) {
                buf.writeBytes(CONTENT.getBytes("UTF-8"));
            }
            buf.writeBytes(SPLIT_T.getBytes("UTF-8"));
            channel.writeInbound(buf);
        }
    }

    @Test
    public void testLengthFieldBasedFrameDecoder() throws UnsupportedEncodingException {
        // 定义一个 基于长度域解码器
        LengthFieldBasedFrameDecoder decoder = new LengthFieldBasedFrameDecoder(
                1024, 0, 4, 0, 4);
        ChannelInitializer<EmbeddedChannel> i = new ChannelInitializer<>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(decoder);
                ch.pipeline().addLast(new StringDecoder(ByteUtil.utf8Code));
                ch.pipeline().addLast(new StringProcessHandler());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        for (int j = 0; j < 3; j++) {
            int num = 3;
            ByteBuf buf = Unpooled.buffer();
            byte[] bytes = CONTENT.getBytes("UTF-8");
            // 首先写入头部 head，也就是后面的数据长度
            buf.writeInt(bytes.length * num);
            // 然后写入 content
            for (int k = 0; k < num; k++) {
                buf.writeBytes(bytes);
            }
            channel.writeInbound(buf);
        }
    }

    @Test
    public void testLengthFieldBasedFrameDecoder1() throws UnsupportedEncodingException {
        LengthFieldBasedFrameDecoder decoder = new LengthFieldBasedFrameDecoder(
                1024, 0, 4, 0, 4);
        ChannelInitializer<EmbeddedChannel> i = new ChannelInitializer<>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(decoder);
                ch.pipeline().addLast(new StringDecoder(ByteUtil.utf8Code));
                ch.pipeline().addLast(new StringProcessHandler());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        for (int j = 1; j <= 3; j++) {
            ByteBuf buf = Unpooled.buffer();
            String s = "第 " + j + " 次发送 -> " + CONTENT;
            byte[] bytes = s.getBytes("UTF-8");
            buf.writeInt(bytes.length);
            System.out.println("bytes length = " + bytes.length);
            buf.writeBytes(bytes);
            channel.writeInbound(buf);
        }
    }

    @Test
    public void testLengthFieldBasedFrameDecoder2() throws UnsupportedEncodingException {
        LengthFieldBasedFrameDecoder decoder = new LengthFieldBasedFrameDecoder(
                1024, 0, 4, 2, 6);
        ChannelInitializer<EmbeddedChannel> i = new ChannelInitializer<>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(decoder);
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        for (int j = 1; j <= 3; j++) {
            ByteBuf buf = Unpooled.buffer();
            String s = j + " 次发送 -> " + CONTENT;
            byte[] bytes = s.getBytes("UTF-8");
            // 首先 写入头部 head，包括 后面的数据长度
            buf.writeInt(bytes.length);
            buf.writeChar(VERSION);
            // 然后 写入  content
            buf.writeBytes(bytes);
            channel.writeInbound(buf);
        }
    }

    @Test
    public void testLengthFieldBasedFrameDecoder3() throws UnsupportedEncodingException {
        LengthFieldBasedFrameDecoder decoder = new LengthFieldBasedFrameDecoder(
                1024, 2, 4, 4, 10);
        ChannelInitializer<EmbeddedChannel> i = new ChannelInitializer<>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(decoder);
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        for (int j = 1; j <= 3; j++) {
            ByteBuf buf = Unpooled.buffer();
            String s = j + " 次发送 -> " + CONTENT;
            byte[] bytes = s.getBytes("UTF-8");
            buf.writeChar(VERSION);
            buf.writeInt(bytes.length);
            buf.writeInt(MAGIC_CODE);
            buf.writeBytes(bytes);
            channel.writeInbound(buf);
        }
    }


}
