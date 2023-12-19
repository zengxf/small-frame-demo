package zxf.main;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import zxf.impl.RpcImpl;

/**
 * <p/>
 * Created by ZXFeng on 2023/12/18
 */
@Slf4j
public class MainServer {

    public static final int port = 9999;

    public static void main(String[] args) throws Exception {
        // 设置 service 接口.
        Server server = ServerBuilder
                .forPort(port)
                .addService(new RpcImpl())
                .build()
                .start();
        log.info("gRPC 服务端启动成功, 端口号: [{}]", port);

        server.awaitTermination();
    }

}
