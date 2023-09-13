package test.socket.nio;

import lombok.extern.slf4j.Slf4j;
import util.SleepUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * ref: https://blog.csdn.net/m0_45406092/article/details/111351211
 */
@Slf4j
public class MyNioClient {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        SocketChannel clientChannel = SocketChannel.open();
        clientChannel.configureBlocking(false);                     // 设置非阻塞
        clientChannel.connect(new InetSocketAddress("localhost", MyNioServer.PORT)); // 连接
        clientChannel.register(selector, SelectionKey.OP_CONNECT);  // 注册

        out:
        while (true) {
            if (selector.select(500) == 0) {
                log.info("..");
                continue;
            }
            Iterator<SelectionKey> keyIt = selector.selectedKeys().iterator();
            while (keyIt.hasNext()) {
                SelectionKey key = keyIt.next();
                keyIt.remove();
                if (key.isConnectable()) {
                    SocketChannel sc = (SocketChannel) key.channel();   // 就是 clientChannel 对象
                    if (sc.finishConnect()) {                           // 等待连接完成
                        log.info("连接成功!");
                        byte[] msg = "中-Test 123...".getBytes(StandardCharsets.UTF_8);
                        log.info("send-msg-len: [{}]", msg.length);
                        ByteBuffer write = ByteBuffer.allocate(msg.length);
                        write.put(msg);
                        write.flip();
                        sc.write(write);
                        break out; // 退出
                    } else {
                        log.info("还未完成连接...");
                    }
                }
            }
        }

        SleepUtils.second(2);
        log.info("exit...");
    }

}