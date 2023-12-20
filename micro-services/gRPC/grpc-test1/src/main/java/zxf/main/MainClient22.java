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
public class MainClient22 extends MainClient {

    public static void main(String[] args) throws InterruptedException {
        // 1. 拿到一个通信的 channel
        ManagedChannel server = getServer();
        try {
            // 2.拿到道理对象
            RpcServiceGrpc.RpcServiceStub api = RpcServiceGrpc.newStub(server);
            // 3. 请求
            StreamObserver<RpcResponse> responseObserver = new StreamObserver<>() {
                @Override
                public void onNext(RpcResponse res) {
                    // 4. 输出结果
                    log.info("MainClient22 - res: [{}]", res.getRes());
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
            StreamObserver<RpcRequest> reqObs = api.getDataList2(responseObserver);
            for (int i = 1; i <= 2; i++) {
                RpcRequest req = req("test-" + (i * 200));
                reqObs.onNext(req);
                Thread.sleep(100L);
            }
            Thread.sleep(2000L);
            log.info("exit -----");
        } finally {
            // 5. 关闭 channel, 释放资源.
            server.shutdown();
        }
    }

}
