package ssl;

import lombok.extern.slf4j.Slf4j;
import utils.IoUtils;
import utils.SSLContextHelper;
import utils.SystemConfig;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/11.
 */
@Slf4j
public class SSLEchoServer extends IoUtils implements SystemConfig {

    // 服务端 SSL 监听套接字
    static SSLServerSocket serverSocket;

    public static void start() {
        try {
            // 创建服务端 SSL 上下文实例
            SSLContext serverSSLContext = SSLContextHelper.createServerSSLContext();
            SSLServerSocketFactory sslServerSocketFactory = serverSSLContext.getServerSocketFactory();
            // 通过服务端 SS L上下文实例，创建服务端 SSL 监听套接字
            serverSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(SOCKET_SERVER_PORT);
            // serverSocket.setNeedClientAuth(false);  // 单向认证：在服务端设置不需要验证对端身份，无需客户端证实自己的身份
            serverSocket.setNeedClientAuth(true);   // 双向认证
            // false 表示：在握手的时候，使用服务端模式
            serverSocket.setUseClientMode(false);

            String[] supported = serverSocket.getEnabledCipherSuites();
            serverSocket.setEnabledCipherSuites(supported);
            log.info("SSL OIO ECHO 服务已经启动 {}:{}", SOCKET_SERVER_IP, SOCKET_SERVER_PORT);
            // 监听和接收客户端连接
            while (!Thread.interrupted()) {
                Socket client = serverSocket.accept();
                System.out.println(client.getRemoteSocketAddress());
                // 向客户端发送接收到的字节序列
                OutputStream output = client.getOutputStream();
                // 当一个普通 socket 连接上来, 这里会抛出异常
                InputStream input = client.getInputStream();
                byte[] buf = new byte[1024];
                int len = 0;
                StringBuffer buffer = new StringBuffer();
                while ((len = input.read(buf)) != -1) {
                    String sf = new String(buf, 0, len, "UTF-8");
                    log.info("服务端收到：{}", sf);
                    buffer.append(sf);
                    if (sf.contains("\r\n\r\n")) {
                        break;
                    }
                }
                // 发送消息到客户端
                output.write(buffer.toString().getBytes("UTF-8"));
                output.flush();
                // 关闭 socket 连接
                closeQuietly(input);
                closeQuietly(output);
                closeQuietly(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally { // 关闭s erverSocket 监听套接字
            closeQuietly(serverSocket);
        }
    }

    public static void main(String[] args) {
        /**
         * 使用 WLAN ip
         * C:/xx> ipconfig
         * 无线局域网适配器 WLAN:
         *    连接特定的 DNS 后缀 . . . . . . . : xx.com
         *    本地链接 IPv6 地址. . . . . . . . : fe80::cc5b:70d9:4740:208d%7
         *    IPv4 地址 . . . . . . . . . . . . : 10.10.48.186
         *    子网掩码  . . . . . . . . . . . . : 255.255.255.0
         *    默认网关. . . . . . . . . . . . . : 10.10.48.1
         * -----------------------------
         * 添加路由回环（mask 要使用 4 个 255）
         *     route add 10.10.48.186 mask 255.255.255.255 10.10.48.1
         * WireShark 抓包过滤命令
         *     ip.src == 10.10.48.186 && ip.dst == 10.10.48.186 && ssl
         * 删除路由回环
         *     route delete 10.10.48.186 mask 255.255.255.255 10.10.48.1
         */
        // System.setProperty("javax.net.debug", "ssl,handshake");
        System.setProperty("javax.net.debug", "ssl,handshake,data,trustmanager");
        System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
        start();
    }

}
