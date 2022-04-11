package test;

import feign.Feign;
import feign.Logger;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.List;
import java.util.Map;

@Slf4j
public class TestMain {

    public static void main(String[] args) {
        testBinance();
        // testGitHub();
//        testFindUsers();
//        testCreateUser();
    }

    static void testBinance() {
        Map<String, Object> map = Map.of(
                "symbol", "BTCUSDT",
                "interval", "1m",
                "startTime", 1649519286447L,
                "limit", 10
        );
        BinanceService binSer = getService(BinanceService.class, "https://api.binance.com");
        // BinanceService binSer = getService(BinanceService.class, "https://testnet.binance.vision");
        List<List<Object>> list = binSer.kLines(map);
        list.forEach(arr -> {
            log.info("arr: [{}]", arr);
        });
    }

    static void testCreateUser() {
        UserService userService = getService(UserService.class, "https://gorest.co.in");
        UserService.UserDto dto = new UserService.UserDto()
                .setName("test")
                .setEmail("test1@sina.cn")
                .setGender("male")
                .setStatus("active");
        log.info("create-user: [{}]", dto);
        try {
            String token = "Bearer b2e9d3ea93b15934a38adac492d3663c72756998c5fd2d3d6b324121e15fb0b9";
            Object res = userService.createUser(token, dto);
            log.info("create-user-res: [{}]", res);
        } catch (Exception e) {
            log.error("创建出错！", e);
        }
    }

    static void testFindUsers() {
        UserService userService = getService(UserService.class, "https://gorest.co.in");
//        UserService.ListDto dto = userService.findUsers(2);
        UserService.ListDto dto = userService.findUsers2(Map.of("page", 2));
        log.info("meta: [{}]", dto.getMeta());
        dto.getData().forEach(user -> {
            log.info("user: [{}]", user);
        });
    }

    static void testGitHub() {
        GitHub github = getService(GitHub.class, "https://api.github.com");
        List<GitHub.Contributor> contributors = github.contributors("OpenFeign", "feign");
        for (GitHub.Contributor contributor : contributors) {
            System.out.println(contributor.login + " (" + contributor.contributions + ")");
        }
    }

    static <T> T getService(Class<T> clazz, String website) {
        // 设置代理
        HttpHost proxy = new HttpHost("localhost", 7078);
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setProxy(proxy)
                .build();
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultRequestConfig(defaultRequestConfig)
                .build();
        ApacheHttpClient client = new ApacheHttpClient(httpclient);

        T service = Feign.builder()
                .client(client)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger()).logLevel(Logger.Level.FULL)
                .decode404()
                .target(clazz, website);
        return service;
    }

}
