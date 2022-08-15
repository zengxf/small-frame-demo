package test.echo_service.sockets;

import test.util.SystemConfig;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/15.
 */
public class SecureChatClient2 {

    public static void main(String[] args) throws Exception {
        new SecureChatClient().start(
                SystemConfig.SOCKET_SERVER_IP, SystemConfig.SOCKET_SERVER_PORT
        );
    }

}
