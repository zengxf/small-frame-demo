package cn.simple.test.new_features.jdk9.web;


import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by zengxf on 2017/11/24.
 */
public class TestHttpRequest {

    public static void main(String[] arr) throws URISyntaxException, IOException, InterruptedException {
        URI uri = new URI("http://www.baidu.com");
        HttpClient client = HttpClient.newHttpClient();

        // GET
        HttpResponse<String> response = client.send(
                HttpRequest
                        .newBuilder(uri)
                        .headers("Foo", "foovalue", "Bar", "barvalue")
                        .GET()
                        .build(),
                HttpResponse.BodyHandler.asString()
        );
        int statusCode = response.statusCode();
        String body = response.body();

        System.out.println("status code: " + statusCode);
        System.out.println(body);
    }

}
