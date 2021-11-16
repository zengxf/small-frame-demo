package test;

import java.util.concurrent.Future;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.async.methods.SimpleRequestProducer;
import org.apache.hc.client5.http.async.methods.SimpleResponseConsumer;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.message.StatusLine;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.apache.hc.core5.util.Timeout;

/**
 * <br/>
 * Created by ZXFeng on  2021/11/16.
 */
@Slf4j
public class TestAsync {

    public static void main(String[] args) throws Exception {
        final IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setSoTimeout(Timeout.ofSeconds(5))
                .build();

        final CloseableHttpAsyncClient client = HttpAsyncClients.custom()
                .setIOReactorConfig(ioReactorConfig)
                .build();

        client.start();

        final HttpHost target = new HttpHost("localhost", 9066);
        final String[] requestUris = new String[]{"/user/find-all", "/user/find-one/id-1"};

        for (final String requestUri : requestUris) {
            final SimpleHttpRequest request = SimpleRequestBuilder.get()
                    .setHttpHost(target)
                    .setPath(requestUri)
                    .build();

            log.info("执行请求：" + request);
            final Future<SimpleHttpResponse> future = client.execute(
                    SimpleRequestProducer.create(request),
                    SimpleResponseConsumer.create(),
                    new FutureCallback<SimpleHttpResponse>() {
                        @Override
                        public void completed(final SimpleHttpResponse response) {
                            log.info("请求完成：");
                            log.info("[{}] -> [{}]", request, new StatusLine(response));
                            log.info("Body: [{}]", response.getBody());
                            log.info("-------");
                        }

                        @Override
                        public void failed(final Exception ex) {
                            log.error("请求失败！", ex);
                        }

                        @Override
                        public void cancelled() {
                            log.warn("请求取消！");
                        }

                    });
//            future.get();
        }

        log.info("Shutting down");
        client.close(CloseMode.GRACEFUL);
    }

}
