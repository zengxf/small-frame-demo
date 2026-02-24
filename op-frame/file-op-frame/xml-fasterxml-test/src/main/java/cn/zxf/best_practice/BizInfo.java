package cn.zxf.best_practice;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * <p/>
 * Created by ZXFeng on 2026/2/24
 */
@JacksonXmlRootElement(localName = "biz_info")
@Data
public class BizInfo {

    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(localName = "account_id")
    private String accountId;


    public BizInfo(String type, String id, String accountId) {
        this.type = type;
        this.id = id;
        this.accountId = accountId;
    }

}
