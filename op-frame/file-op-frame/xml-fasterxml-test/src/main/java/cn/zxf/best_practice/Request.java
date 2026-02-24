package cn.zxf.best_practice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * <p/>
 * Created by ZXFeng on 2026/2/24
 */
@JacksonXmlRootElement(localName = "request_0")
@Data
public class Request<T> {

    @JacksonXmlProperty(isAttribute = true)
    private String serviceApp;

    @JacksonXmlProperty(isAttribute = true)
    private String id;


    @JsonProperty("zip_1")
    private T zip;


    public Request(String serviceApp, String id, T zip) {
        this.serviceApp = serviceApp;
        this.id = id;
        this.zip = zip;
    }

}
