package test.jdkapi.net;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestHttpClient {

    public static void main( String[] args ) throws Exception {
        // String url = "http://openjdk.java.net/"; // 合法证书测试
        String url = "https://591234x.com/788789/31139.html"; // 未知证书测试

        HttpClient http = SslSetup.httpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri( URI.create( url ) )
                .header( "Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3" )
                // .header( "Host", "591234x.com" )
                // .header( "Connection", "keep-alive" )
                .header( "Sec-Fetch-Mode", "navigate" )
                .header( "Sec-Fetch-Site", "none" )
                .header( "Sec-Fetch-User", "?1" )
                .header( "Upgrade-Insecure-Requests", "1" )
                .header( "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36" )
                .GET()
                .build();
        HttpResponse<String> response = http.send( request, HttpResponse.BodyHandlers.ofString() );

        log.info( "status: [{}]", response.statusCode() );
        log.info( "body: [{}]", response.body() );
    }

}
