package cn.simple.test.new_features.jdk9.jih;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by zengxf on 2017/10/9.
 */
public class TestHttpClient {

    public static void main(String[] arr) throws URISyntaxException, IOException, InterruptedException {
        String responseBody = HttpClient.newHttpClient()
                .send(
                        HttpRequest.newBuilder(new URI("https://www.baidu.com/"))
                                .version(HttpClient.Version.HTTP_2)
                                .GET()
                                .build(),
                        HttpResponse.BodyHandler.asString())
                .body();
        System.out.println(responseBody);

    }

}
