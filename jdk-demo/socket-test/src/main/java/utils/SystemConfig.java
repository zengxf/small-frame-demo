package utils;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/11.
 */
public interface SystemConfig {

    String SOCKET_SERVER_IP = "10.10.48.186";
    int SOCKET_SERVER_PORT = 12686;

    static String getKeystoreDir() {
        return "D:/Data/test/key";
    }

}
