package test.biz;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;

import java.math.BigDecimal;

/**
 * Parquet 字段访问工具类
 * <p/>
 * Created by ZXFeng on 2026/1/26
 */
public class DataAccessUtils {

    /**
     * 安全地获取字段值
     */
    public static Object getValue(GenericRecord record, String fieldName) {
        if (record == null || fieldName == null) {
            return null;
        }
        try {
            Schema schema = record.getSchema();
            Schema.Field field = schema.getField(fieldName);
            if (field != null) {
                return record.get(fieldName);
            }
        } catch (Exception e) {
            // 字段不存在或无法访问
        }
        return null;
    }

    /**
     * 安全地获取字符串字段值
     */
    public static String getString(GenericRecord record, String fieldName) {
        Object value = getValue(record, fieldName);
        return value != null ? value.toString() : null;
    }

    /**
     * 安全地获取 Long 字段值
     */
    public static Long getLong(GenericRecord record, String fieldName) {
        Object value = getValue(record, fieldName);
        if (value == null) {
            return null;
        }
        try {
            if (value instanceof Long) {
                return (Long) value;
            } else if (value instanceof Number) {
                return ((Number) value).longValue();
            } else {
                return Long.valueOf(value.toString());
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 安全地获取 Integer 字段值
     */
    public static Integer getInteger(GenericRecord record, String fieldName) {
        Object value = getValue(record, fieldName);
        if (value == null) {
            return null;
        }
        try {
            if (value instanceof Integer) {
                return (Integer) value;
            } else if (value instanceof Number) {
                return ((Number) value).intValue();
            } else {
                return Integer.valueOf(value.toString());
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 安全地获取 BigDecimal 字段值
     */
    public static BigDecimal getBigDecimal(GenericRecord record, String fieldName) {
        Object value = getValue(record, fieldName);
        if (value == null) {
            return null;
        }
        try {
            if (value instanceof BigDecimal) {
                return (BigDecimal) value;
            } else if (value instanceof Number) {
                return new BigDecimal(value.toString());
            } else {
                return new BigDecimal(value.toString());
            }
        } catch (Exception e) {
            return null;
        }
    }

}

