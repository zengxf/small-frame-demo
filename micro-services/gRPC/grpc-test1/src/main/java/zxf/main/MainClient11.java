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
public class MainClient11 extends MainClient {

    public static void main(String[] args) {
        // 1. 拿到一个通信的 channel
        ManagedChannel server = getServer();
        try {
            // 2.拿到道理对象
            RpcServiceGrpc.RpcServiceBlockingStub api = RpcServiceGrpc.newBlockingStub(server);
            RpcRequest req = req("test-11");
            // 3. 请求
            RpcResponse res = api.getData(req);
            // 4. 输出结果
            log.info("res: [{}]", res.getRes());

            // -----------
            req = req("test-22");
            res = api.getData(req);
            log.info("res: [{}]", res.getRes());
            // -----------
            req = req("test-33");
            res = api.getData(req);
            log.info("res: [{}]", res.getRes());
        } finally {
            // 5. 关闭 channel, 释放资源.
            server.shutdown();
        }
    }


}
