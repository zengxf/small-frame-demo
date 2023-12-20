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
        log.info("req: {}", req.getUserName());
        String res = String.format("res: %s - %s", req.getUserName(), LocalTime.now());
        RpcResponse rpcRes = RpcResponse.newBuilder()
                .setRes(res)
                .build();
        resObs.onNext(rpcRes);
        resObs.onCompleted();
    }

}
