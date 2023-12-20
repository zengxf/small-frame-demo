package zxf.main;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import zxf.rpc.RpcRequest;

/**
 * <p/>
 * Created by ZXFeng on 2023/12/20
 */
public class MainClient {

    private static final String host = "127.0.0.1";
    private static final int serverPort = MainServer.port;

    static ManagedChannel getServer() {
        ManagedChannel server = ManagedChannelBuilder
                .forAddress(host, serverPort)
                .usePlaintext()
                .build();
        return server;
    }

    static RpcRequest req(String name) {
        return RpcRequest
                .newBuilder()
                .setUserName(name)
                .build();
    }

}
