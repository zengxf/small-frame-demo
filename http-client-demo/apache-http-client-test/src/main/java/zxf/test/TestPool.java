package zxf.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <br/>
 * Created by ZXFeng on 2021/11/30.
 */
@Slf4j
public class TestPool {

    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(14);
        for (int index = 1; index <= 20; index++) {
            int i = index;
//            RequestDto dto = new RequestDto().setUrl("https://gorest.co.in/public/v1/users/" + i);
            RequestDto dto = new RequestDto().setUrl("http://www.baidu.com/s?wd=" + i);
            es.execute(() -> {
                log.info("------> Request Dto: " + dto);
                try {
                    HttpResponse res = HttpRequester.request(dto);
                    int status = res.getStatusLine().getStatusCode();
                    log.info("<------ res-status: " + status + " by " + i);
//                    EntityUtils.consume(res.getEntity()); // 归还连接
//                    EntityUtils.consume(res.getEntity()); // 归还连接
                } catch (Exception e) {
                    log.error("请求出错！", e);
                }
            });
        }
    }

}
