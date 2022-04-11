package test;

import feign.QueryMap;
import feign.RequestLine;

import java.util.List;
import java.util.Map;

/**
 * <br/>
 * Created by ZXFeng on 2022/4/11.
 */
public interface BinanceService {

    @RequestLine("GET /api/v3/klines")
    List<List<Object>> kLines(
            @QueryMap Map<String, Object> map
    );

}
