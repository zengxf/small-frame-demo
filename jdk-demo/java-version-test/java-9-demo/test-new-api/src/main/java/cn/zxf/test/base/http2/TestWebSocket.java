package cn.zxf.test.base.http2;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.WebSocket;

import java.net.URI;
import java.util.concurrent.CompletionStage;

/**
 * Created by zengxf on 2018/9/12.
 */
public class TestWebSocket {

    public static void main(String[] arr) throws Exception {
        URI serverUri = new URI("ws://localhost:8080/webapp/servertime");
        WebSocket.Listener listener = new WebSocket.Listener() {
            @Override
            public CompletionStage<?> onText(WebSocket webSocket, CharSequence message, WebSocket.MessagePart part) {
                webSocket.request(1);
                System.out.println("Server: " + message);
                return null;
            }
        };
        HttpClient.newHttpClient()
                .newWebSocketBuilder(serverUri, listener)
                .buildAsync()
                .whenComplete((WebSocket webSocket, Throwable t) -> {
                });
    }

}
