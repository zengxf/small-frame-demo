package zxf.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <br/>
 * Created by ZXFeng on 2021/11/30.
 */
public class JsonUtils {

    public static String toJson(Object obj) throws JsonProcessingException {
        if (obj == null)
            return "";
        ObjectMapper om = getObjectMapper();
        String json = om.writeValueAsString(obj);
        return json;
    }

    public static Object toObject(String json) throws JsonProcessingException {
        ObjectMapper om = getObjectMapper();
        return om.readValue(json, Object.class);
    }

    private static ObjectMapper getObjectMapper() {
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

}
