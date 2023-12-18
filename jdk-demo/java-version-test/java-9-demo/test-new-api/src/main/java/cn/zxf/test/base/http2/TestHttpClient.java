package cn.zxf.test.base.http2;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpHeaders;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by zengxf on 2018/9/12.
 */
public class TestHttpClient {

    public static void main(String[] arr) throws Exception {
//        testGoogle();
//        testHttp();
        testHttpTrailer();
    }

    static void testHttpTrailer() throws URISyntaxException, IOException, InterruptedException {
        URI uri = new URI("http://test.core.hunterplus.net");
        HttpResponse<String> response = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build()
                .send(
                        HttpRequest.newBuilder().uri(uri).GET().build(),
                        HttpResponse.BodyHandler.asString()
                );
        String body = response.body();
        System.out.println(body);

        response.trailers() // 当前还不支持，jdk 10 直接删了
                .whenComplete((HttpHeaders trailers, Throwable t) -> {
                    if (t == null) {
                        trailers.map()
                                .entrySet()
                                .forEach(System.out::println);
                    } else {
                        t.printStackTrace();
                    }
                });
    }

    static void testHttp() throws URISyntaxException, InterruptedException {
        URI calcUri = new URI("http://test.core.hunterplus.net");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(calcUri)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "text/plain")
                .GET()
                .build();
        HttpClient.newHttpClient()
                .sendAsync(request, HttpResponse.BodyHandler.asString())
                .whenComplete(TestHttpClient::processResponse);
        Thread.sleep(1000);
    }

    static void processResponse(HttpResponse<String> response, Throwable t) {
        if (t == null) {
            System.out.println("Response Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } else {
            System.out.println("An exception occurred while " + "processing the HTTP request. Error: " + t.getMessage());
        }
    }

    static void testGoogle() throws URISyntaxException, IOException, InterruptedException {
        URI uri = new URI("https://www.google.com.hk");
        String responseBody = HttpClient.newHttpClient()
                .send(
                        HttpRequest.newBuilder(uri)
                                .GET()
                                .build(),
                        HttpResponse.BodyHandler
                                .asString()
                )
                .body();
        System.out.println(responseBody);
        // http2
        HttpClient httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

}
