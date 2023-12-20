package zxf.impl;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import zxf.rpc.RpcRequest;
import zxf.rpc.RpcResponse;
import zxf.rpc.RpcServiceGrpc;

import java.time.LocalTime;

/**
 * <p/>
 * Created by ZXFeng on 2023/12/18
 */
@Slf4j
public class RpcImpl extends RpcServiceGrpc.RpcServiceImplBase {

    @Override
    public void getData(RpcRequest req, StreamObserver<RpcResponse> resObs) {
        log.info("getData - req: {}", req.getUserName());
        String res = String.format("res: %s - %s", req.getUserName(), LocalTime.now());
        RpcResponse rpcRes = RpcResponse.newBuilder()
                .setRes(res)
                .build();
        resObs.onNext(rpcRes);  // 只能返回一个
        resObs.onCompleted();
    }

    @Override
    @Deprecated // 只一次
    public StreamObserver<RpcRequest> getData2(StreamObserver<RpcResponse> resObs) {
        return new StreamObserver<>() {
            @Override
            public void onNext(RpcRequest req) {
                log.info("getData2 - req: {}", req.getUserName());
                // 只能返回一个
                String res = String.format("res: %s - %s - %s", req.getUserName(), LocalTime.now(), 20);
                RpcResponse rpcRes = RpcResponse.newBuilder()
                        .setRes(res)
                        .build();
                resObs.onNext(rpcRes);
                resObs.onCompleted();   // 需要此操作（但客户端只能请求一次）
            }

            @Override
            public void onError(Throwable t) {
                log.error("处理出错(getData2)", t);
            }

            @Override
            public void onCompleted() {
                log.info("处理完成");
            }
        };
    }

    @Override
    public void getDataList(RpcRequest req, StreamObserver<zxf.rpc.RpcResponse> resObs) {
        log.info("getDataList - req: {}", req.getUserName());
        for (int i = 1; i <= 3; i++) {
            String res = String.format("res: %s - %s - %s", req.getUserName(), LocalTime.now(), i * 100);
            RpcResponse rpcRes = RpcResponse.newBuilder()
                    .setRes(res)
                    .build();
            resObs.onNext(rpcRes);
            // resObs.onCompleted();    // 不能此操作
        }
    }

    @Override
    public StreamObserver<RpcRequest> getDataList2(StreamObserver<RpcResponse> resObs) {
        return new StreamObserver<>() {
            @Override
            public void onNext(RpcRequest req) {
                log.info("getDataList2 - req: {}", req.getUserName());
                for (int i = 1; i <= 3; i++) {
                    String res = String.format("res: %s - %s - %s", req.getUserName(), LocalTime.now(), i * 200);
                    RpcResponse rpcRes = RpcResponse.newBuilder()
                            .setRes(res)
                            .build();
                    resObs.onNext(rpcRes);
                    // resObs.onCompleted();    // 不能此操作
                }
            }

            @Override
            public void onError(Throwable t) {
                log.error("处理出错(getDataList2)", t);
            }

            @Override
            public void onCompleted() {
                log.info("处理完成");
            }
        };
    }

}
