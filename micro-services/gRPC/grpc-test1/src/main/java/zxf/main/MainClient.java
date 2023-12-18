package zxf.main;


import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import zxf.rpc.RpcRequest;
import zxf.rpc.RpcResponse;
import zxf.rpc.RpcServiceGrpc;

/**
 * <p/>
 * Created by ZXFeng on 2023/12/18
 */
@Slf4j
public class MainClient {

    private static final String host = "127.0.0.1";
    private static final int serverPort = MainServer.port;

    public static void main(String[] args) {
        // 1. 拿到一个通信的 channel
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(host, serverPort)
                .usePlaintext()
                .build();
        try {
            // 2.拿到道理对象
            RpcServiceGrpc.RpcServiceBlockingStub server = RpcServiceGrpc.newBlockingStub(managedChannel);
            RpcRequest req = RpcRequest
                    .newBuilder()
                    .setUserName("test 11")
                    .build();
            // 3. 请求
            RpcResponse res = server.getData(req);
            // 4. 输出结果
            log.info("res: [{}]", res.getRes());
        } finally {
            // 5. 关闭 channel, 释放资源.
            managedChannel.shutdown();
        }
    }

}
