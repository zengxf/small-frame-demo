package zxf.main;


import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import zxf.rpc.RpcRequest;
import zxf.rpc.RpcResponse;
import zxf.rpc.RpcServiceGrpc;

/**
 * <p/>
 * Created by ZXFeng on 2023/12/18
 */
@Slf4j
@Deprecated // 不是想要的
public class MainClient12 extends MainClient {

    public static void main(String[] args) throws InterruptedException {
        // 1. 拿到一个通信的 channel
        ManagedChannel server = getServer();
        try {
            // 2.拿到道理对象
            RpcServiceGrpc.RpcServiceStub api = RpcServiceGrpc.newStub(server);
            // 3. 请求
            StreamObserver<RpcResponse> responseObserver = new StreamObserver<>() {
                @Override
                public void onNext(RpcResponse res) {   // 只接收到一次
                    // 4. 输出结果
                    log.info("MainClient12 - res: [{}]", res.getRes());
                }

                @Override
                public void onError(Throwable t) {
                    log.error("err!", t);
                }

                @Override
                public void onCompleted() {
                    log.info("Completed --------------------");
                }
            };
            // -----------
            StreamObserver<RpcRequest> reqObs = api.getData2(responseObserver);
            for (int i = 1; i <= 3; i++) {
                log.info("MainClient12 - req - " + (i * 20));
                RpcRequest req = req("test-" + (i * 20));
                reqObs.onNext(req);
                Thread.sleep(500L);
            }
            Thread.sleep(4000L);
            log.info("exit -----");
        } finally {
            // 5. 关闭 channel, 释放资源.
            server.shutdown();
        }
    }


}
