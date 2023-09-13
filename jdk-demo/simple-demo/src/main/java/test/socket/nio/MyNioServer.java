package test.socket.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * ref: https://blog.csdn.net/m0_45406092/article/details/111351211
 */
@Slf4j
public class MyNioServer {

    static final int PORT = 9900;

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);                    // 设置非阻塞
        server.register(selector, SelectionKey.OP_ACCEPT);  // 注册
        ServerSocket serverSocket = server.socket();
        serverSocket.bind(new InetSocketAddress(PORT));     // 启动

        log.info("开启监听...");
        while (true) {
            if (selector.select(10_000) == 0) {
                log.info("..");
                continue;
            }
            Iterator<SelectionKey> keyIt = selector.selectedKeys().iterator();
            while (keyIt.hasNext()) {
                SelectionKey key = keyIt.next();
                keyIt.remove(); // 移除，防重复处理
                if (key.isAcceptable()) {
                    log.info("有新的连接!");
                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);                    // 设置非阻塞
                    sc.register(selector, SelectionKey.OP_READ);    // 注册
                } else if (key.isReadable()) {
                    log.info("有数据可读取!");
                    read(key);
                } else if (key.isValid() && key.isWritable()) {
                    log.info("isWritable!");
                }
            }
        }
    }

    static void read(SelectionKey key) {
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        try {
            int readBytes = sc.read(readBuffer);
            log.info("read-bytes-len: [{}]", readBytes);
            if (readBytes < 0)
                sc.close();
            else {
                readBuffer.flip();
                byte[] bytes = new byte[readBuffer.remaining()];
                readBuffer.get(bytes);
                String msg = new String(bytes, StandardCharsets.UTF_8);
                log.info("接收的消息为：[{}]", msg);
            }
        } catch (Exception e) {
            log.info("读取出错（可能是 Fin 消息）", e); // 对方关闭时，会发送 Fin 消息（Connection reset）
            try {
                sc.close();
            } catch (IOException ex) {
                log.info("关闭 sc 出错", e);
            }
        }
    }

}