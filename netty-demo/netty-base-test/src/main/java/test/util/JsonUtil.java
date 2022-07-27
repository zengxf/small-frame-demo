package test.util;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Map;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/20.
 */
public class JsonUtil {

    public static String pojoToJson(Object obj) {
        try {
            return JsonUtils.toJson(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T jsonToPojo(String json, Class<T> tClass) {
        try {
            return JsonUtils.toObject(json, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> jsonToMap(String json) {
        try {
            return JsonUtils.toObject(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}

class JsonUtils {

    public static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    public static String toJson(Object obj) throws JsonProcessingException {
        if (obj == null)
            return StrUtil.EMPTY;
        String json = mapper.writeValueAsString(obj);
        return json;
    }

    public static <T> T toObject(String json, TypeReference<T> ref) throws JsonProcessingException {
        if (StrUtil.isEmpty(json))
            return null;
        return mapper.readValue(json, ref);
    }

    public static <T> T toObject(String json, Class<T> clazz) throws JsonProcessingException {
        if (StrUtil.isEmpty(json))
            return null;
        return mapper.readValue(json, clazz);
    }

}

