package zxf.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.concurrent.TimeUnit;

/**
 * <br/>
 * Created by ZXFeng on 2021/11/30.
 */
public class HttpRequester {

    public static HttpResponse request(RequestDto dto) throws Exception {
        return new HttpRequester().doRequest(dto);
    }

    private HttpResponse doRequest(
            RequestDto dto
    ) throws Exception {
        HttpUriRequest request = this.buildRequest(dto);
        PoolingHttpClientConnectionManager cm = buildConnManager();

        int timeout = dto.getTimeoutMs();
        RequestConfig requestConfig = RequestConfig.custom()
//                .setConnectionRequestTimeout(timeout) // 连接池的超时时间
                .setConnectTimeout(timeout)
                .setSocketTimeout(timeout)
                .build();
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(cm)
                .build();

        HttpResponse response = httpclient.execute(request);
        return response;
    }

    private HttpUriRequest buildRequest(
            RequestDto dto
    ) throws JsonProcessingException {
        RequestBuilder requestBuilder = RequestBuilder.create(dto.method).setUri(dto.url);

        if (MapUtils.isNotEmpty(dto.params))
            dto.params.forEach((k, v) -> requestBuilder.addParameter(k, v));
        if (MapUtils.isNotEmpty(dto.headers))
            dto.headers.forEach((k, v) -> requestBuilder.addHeader(k, v));

        if (dto.body != null) {
            String json = JsonUtils.toJson(dto.body);
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            requestBuilder.setEntity(entity);
        }

        requestBuilder.addHeader("Accept", ContentType.APPLICATION_JSON.toString());
        requestBuilder.addHeader("Content-type", ContentType.APPLICATION_JSON.toString());
        HttpUriRequest request = requestBuilder.build();

        return request;
    }


    static PoolingHttpClientConnectionManager CM;

    private static synchronized PoolingHttpClientConnectionManager buildConnManager() {
        if (CM != null)
            return CM;
        CM = new PoolingHttpClientConnectionManager(
                30, TimeUnit.MILLISECONDS
        );
        CM.setMaxTotal(10);
        CM.setDefaultMaxPerRoute(4); // 有用
        HttpHost httpHost = HttpHost.create("https://gorest.co.in");
        boolean secure = "https".equalsIgnoreCase(httpHost.getSchemeName());
        HttpRoute httpRoute = new HttpRoute(httpHost, null, secure); // https 默认 secure 为 true
        CM.setMaxPerRoute(httpRoute, 2);

        httpHost = HttpHost.create("http://www.baidu.com");
        httpRoute = new HttpRoute(httpHost);
        CM.setMaxPerRoute(httpRoute, 2);

        return CM;
    }

}
