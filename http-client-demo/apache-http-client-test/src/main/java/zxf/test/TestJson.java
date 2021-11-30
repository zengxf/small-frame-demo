package zxf.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <br/>
 * Created by ZXFeng on 2021/11/30.
 */
@Slf4j
public class TestJson {

    public static void main(String[] args) throws Exception {
        RequestDto dto = new RequestDto()
                .setUrl("https://gorest.co.in/public/v1/users/1");
        log.info("Request Dto: [{}]", dto);

        HttpResponse res = HttpRequester.request(dto);
        int status = res.getStatusLine().getStatusCode();
        log.info("res-status: [{}]", status);

        Map<String, String> header = Stream.of(res.getAllHeaders())
                .collect(Collectors.toMap(
                        Header::getName, Header::getValue,
                        (v1, v2) -> v1 + ";" + v2,
                        LinkedHashMap::new
                ));
        log.info("res-header: [{}]", header);

        String contentStr = EntityUtils.toString(res.getEntity()); // 归还连接池
        Object content = contentStr;
        if (status == 200) {
            content = JsonUtils.toObject(contentStr);
        }
        log.info("res-content: [{}]", content);
    }

}
