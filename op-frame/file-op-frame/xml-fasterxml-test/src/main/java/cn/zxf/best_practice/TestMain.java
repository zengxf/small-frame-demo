package cn.zxf.best_practice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.UUID;

/**
 * <p/>
 * Created by ZXFeng on 2026/2/24
 */
public class TestMain {

    static void main() throws JsonProcessingException {
        String id = UUID.randomUUID().toString();

        BizInfo bizInfo = new BizInfo("risk-info", id, "zxf-666-888");
        CallWrap zip = new CallWrap("Y", bizInfo);
        Request<CallWrap> request = new Request<>("e-buy", id, zip);

        XmlMapper xmlMapper = new XmlMapper();

        // 将 Java 对象转换为 XML 字符串
        String xmlData = xmlMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(request);
        System.out.println(xmlData);
    }

}
