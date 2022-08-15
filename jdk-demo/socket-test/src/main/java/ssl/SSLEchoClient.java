package ssl;

import lombok.extern.slf4j.Slf4j;
import utils.IoUtils;
import utils.SSLContextHelper;
import utils.SystemConfig;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/11.
 */
@Slf4j
public class SSLEchoClient extends IoUtils implements SystemConfig {

    static SSLSocket sslSocket;
    static OutputStream output;
    static InputStream input;

    public static void connect() {
        try {
            // 创建客户端 SSL 上下文
            SSLContext clientSSLContext = SSLContextHelper.createClientSSLContext();
            SSLSocketFactory factory = clientSSLContext.getSocketFactory();
            sslSocket = (SSLSocket) factory.createSocket(SOCKET_SERVER_IP, SOCKET_SERVER_PORT);
            log.info("客户端连接：ssl://{}:{}", SOCKET_SERVER_IP, SOCKET_SERVER_PORT);
            // 在握手的时候，使用客户端模式
            sslSocket.setUseClientMode(true);
            // 设置需要验证对端身份，需验证服务端身份
            sslSocket.setNeedClientAuth(true);
            log.info("连接服务器成功");
            output = sslSocket.getOutputStream();
            input = sslSocket.getInputStream();
            output.write("hello\r\n\r\n".getBytes());
            output.flush();
            log.info("sent hello finished!");
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = input.read(buf)) != -1) {
                log.info("客户端收到：{}", new String(buf, 0, len, "UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeQuietly(output);
            closeQuietly(input);
            closeQuietly(sslSocket);
        }
    }

    public static void main(String[] args) {
        // System.setProperty("javax.net.debug", "ssl,handshake,data,trustmanager");
        System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
        connect();
    }

}