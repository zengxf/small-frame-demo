package cn.zxf.best_practice;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * <p/>
 * Created by ZXFeng on 2026/2/24
 */
@JacksonXmlRootElement(localName = "wrap")
@Data
public class CallWrap {

    @JacksonXmlProperty(isAttribute = true)
    private String plainTextMode;


    @JacksonXmlProperty(localName = "request_1")
    private BizInfo request;


    public CallWrap(String plainTextMode, BizInfo request) {
        this.plainTextMode = plainTextMode;
        this.request = request;
    }

}