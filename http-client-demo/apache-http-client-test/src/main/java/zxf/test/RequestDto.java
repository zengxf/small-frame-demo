package zxf.test;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * <br/>
 * Created by ZXFeng on 2021/11/30.
 */
@Data
@Accessors(chain = true)
public class RequestDto {

    String url;
    String method = "GET";
    int timeoutMs = 3000;
    Map<String, String> params;
    Map<String, String> headers;
    Object body;

}
