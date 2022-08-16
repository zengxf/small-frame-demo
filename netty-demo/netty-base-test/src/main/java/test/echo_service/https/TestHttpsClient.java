package test.echo_service.https;

import lombok.extern.slf4j.Slf4j;
import test.util.IoUtil;
import test.util.SystemConfig;

import javax.net.ssl.*;
import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static test.util.SSLContextHelper.createClientSSLContext;

@Slf4j
public class TestHttpsClient {

    /*** 通过 JDK 自带的 HttpURLConnection 发送 HTTPS 请求 */
    public static void sentRequest(String path) throws Exception {
        // 创建客户端 SSLContext 上下文
        SSLContext clientSSLContext = createClientSSLContext();
        // 创建安全套接字工厂
        SSLSocketFactory factory = clientSSLContext.getSocketFactory();

        // 主机名称校验
        HostnameVerifier hostnameVerifier = (hostname, session) -> {
            // 验证请求的主机名称，这里假设只能请求服务端的配置的主机名
            if (SystemConfig.SOCKET_SERVER_IP.equals(hostname)) {
                return true;
            } else {
                log.error("主机名称校验失败: {}", SystemConfig.SOCKET_SERVER_IP);
                return false;
            }
        };

        // 设置连接的主机名称校验
        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
        // 设置连接的安全套接字工厂
        HttpsURLConnection.setDefaultSSLSocketFactory(factory);
        URL url = new URL(path);
        // 打开连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 获取响应码
        int code = conn.getResponseCode();
        if (code < 400) {
            // 输入流
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            StringBuffer buffer = new StringBuffer();
            // 累积完成的长度
            long finished = 0;
            int len = 0;
            byte[] buff = new byte[1024 * 8];
            while ((len = bis.read(buff)) != -1) {
                buffer.append(new String(buff, 0, len, "UTF-8"));
                finished += len;
                log.info("共完成传输字节数 {}", finished);
            }
            System.out.println("echo = \n" + buffer);
            IoUtil.closeQuietly(bis);
        }
    }

    public static void main(String[] args) throws Exception {
        String path = String.format("https://%s:%d/test?page-index=1&page-size=20",
                SystemConfig.SOCKET_SERVER_IP, SystemConfig.SOCKET_SERVER_PORT
        );
        sentRequest(path);
    }

}
