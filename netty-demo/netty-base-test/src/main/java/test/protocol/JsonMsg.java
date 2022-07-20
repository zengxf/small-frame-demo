package test.protocol;

import lombok.Data;
import test.util.JsonUtil;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/20.
 */
@Data
public class JsonMsg {

    // devId Field(域)
    private int id;
    // content Field(域)
    private String content = " 中文测试 test 发财大吉 ";

    public static JsonMsg parseFromJson(String json) {
        return JsonUtil.jsonToPojo(json, JsonMsg.class);
    }

    public String convertToJson() {
        return JsonUtil.pojoToJson(this);
    }

    public JsonMsg(int id) {
        this.id = id;
        this.content = id + " -> " + this.content;
    }

    public JsonMsg() {
        this.id = 6688;
        this.content = id + " -> " + this.content;
    }

}
