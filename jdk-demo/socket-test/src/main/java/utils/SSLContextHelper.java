package utils;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/11.
 */
@Slf4j
public class SSLContextHelper extends IoUtils {

    public static SSLContext createServerSSLContext() throws Exception {
        // 私钥
        String pass = "123456";
        // 加载 keyStoreFile，生成的秘钥仓库
        String keyStoreFile = SystemConfig.getKeystoreDir() + "/server.jks";
        return createSslContext(pass, keyStoreFile);
    }

    public static SSLContext createClientSSLContext() throws Exception {
        String pass = "123456";
        String keyStoreFile = SystemConfig.getKeystoreDir() + "/client.jks";
        SSLContext sslContext = createSslContext(pass, keyStoreFile);
        return sslContext;
    }

    public static SSLContext createSslContext(String pass, String keyStoreFile) throws Exception {
        char[] passArray = pass.toCharArray();
        KeyStore keyStore = KeyStore.getInstance("JKS");
        // 加载 keyStoreFile， 生成的秘钥仓库
        FileInputStream inputStream = new FileInputStream(keyStoreFile);
        keyStore.load(inputStream, passArray);
        // 调用自定义的方法，创建上下文
        SSLContext sslContext = createSslContext(passArray, keyStore);
        closeQuietly(inputStream);
        return sslContext;
    }


    public static SSLContext createSslContext(char[] passArray, KeyStore keyStore) throws Exception {
        String algorithm = KeyManagerFactory.getDefaultAlgorithm();
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
        kmf.init(keyStore, passArray);

        // 初始化 KeyManagerFactory 之后，创建 SSLContext 并初始化
        SSLContext sslContext = SSLContext.getInstance("SSL");
        // 信任库
        // 如果是单向认证，服务端不需要验证客户端的合法性，此时，TrustManager 可以为空
        X509TrustManagerFacade facade = new X509TrustManagerFacade();
        facade.init(keyStore);
        TrustManager[] trustManagers = new TrustManager[]{facade};
        //TrustManager[] trustManagers = createTrustManagers(keyStore);

        // 安全随机数不需要设置
        sslContext.init(kmf.getKeyManagers(), trustManagers, null);
        return sslContext;
    }

}
