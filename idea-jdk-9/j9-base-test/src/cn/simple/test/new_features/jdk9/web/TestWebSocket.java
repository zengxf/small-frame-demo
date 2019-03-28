package cn.simple.test.new_features.jdk9.web;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.WebSocket;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

/**
 * Created by zengxf on 2017/11/24.
 */
public class TestWebSocket {

    public static void main(String[] arr) throws URISyntaxException, ExecutionException, InterruptedException {
        WebSocketClient client = new WebSocketClient(new URI("ws://localhost:9090/servertime"));
        client.connect();
        client.isClosed();
    }

    static class WebSocketClient {
        private WebSocket webSocket;
        private final URI serverUri;
        private boolean inError = false;

        public WebSocketClient(URI serverUri) {
            this.serverUri = serverUri;
        }

        public boolean isClosed() {
            return (webSocket != null && webSocket.isClosed()) || this.inError;
        }

        public void connect() throws ExecutionException, InterruptedException {
            System.out.println("entry connect ...");
            HttpClient.newHttpClient()
                    .newWebSocketBuilder(serverUri, this.getListener())
                    .buildAsync()
                    .whenComplete(this::statusChanged)
                    .get();
        }

        private void statusChanged(WebSocket webSocket, Throwable t) {
            System.out.println("entry statusChanged ...");
            this.webSocket = webSocket;
            if (t == null) {
                this.talkToServer();
            } else {
                this.inError = true;
                System.out.println("Could not connect to the server." + " Error: " + t.getMessage());
            }
        }

        private void talkToServer() {
            System.out.println("entry talkToServer ...");
//            webSocket.request(1);
            webSocket.sendText("hello-123", true);
        }

        private WebSocket.Listener getListener() {
            return new WebSocket.Listener() {
                @Override
                public void onOpen(WebSocket webSocket) {
                    System.out.println("------------ A WebSocket has been opened ...");
//                    webSocket.request(1);
                }

                @Override
                public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
//                    webSocket.sendClose(1000, "none"); // 先不关闭
                    System.out.println("------------ The WebSocket is closed." + " Close Code: " + statusCode + ", Close Reason: " + reason);
                    return null;
                }

                @Override
                public void onError(WebSocket webSocket, Throwable t) {
                    System.out.println("------------ An error occurred: " + t.getMessage());
                }

                @Override
                public CompletionStage<?> onText(WebSocket WebSocket, CharSequence message, WebSocket.MessagePart part) {
                    System.out.println("------------- Server: " + message);
                    webSocket.request(1);
                    return null;
                }
            };
        }
    }
}
