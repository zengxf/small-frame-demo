package cn.zxf.easypoi.demo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <br>
 * Created by ZXFeng on 2023/8/11
 */
public class DataUtils {

    public static List<DynamicDTO> getData() {
        List<DynamicDTO> list = new ArrayList<>();
        IntStream.range(1, 10).forEach(i -> {
            Map<String, String> map = new HashMap<>();
            map.put("map_k1", "V1-" + i);
            map.put("map_k2", "V2-" + i);
            map.put("map_k3", "V3-" + i);

            UserDto user = new UserDto()
                    .setName("测试-" + i)
                    .setAge(100 + i)
                    .setCreateDate(new Date())
                    .setCreateDate2(LocalDate.now().plusDays(i));

            DynamicDTO dto = new DynamicDTO()
                    .setName("中-test-1")
                    .setAge("22-1")
                    .setMap(map)
                    .setUser(user);

            list.add(dto);
        });
        return list;
    }

    public static List<Map<String, Object>> getDtoMapData() {
        List<DynamicDTO> list = getData();
        List<Field> fields = getFields();
        return list.stream()
                .map(dto -> {
                    Map<String, Object> map = new HashMap<>();
                    fields.forEach(field -> {
                        try {
                            field.setAccessible(true);
                            Object obj = field.get(dto);
                            map.put(field.getName(), obj);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    if (dto.getMap() != null)
                        map.putAll(dto.getMap());
                    return map;
                })
                .collect(Collectors.toList());
    }

    private static List<Field> getFields() {
        Field[] fields = DynamicDTO.class.getDeclaredFields();
        return Stream.of(fields)
                .filter(field -> Objects.nonNull(field.getAnnotation(Excel.class)))
                .collect(Collectors.toList());
    }

    public static List<ExcelExportEntity> dtoExportEntities() {
        List<ExcelExportEntity> entity = new ArrayList<>();
        entity.add(buildExcelEntity("姓名", "name"));
        entity.add(buildExcelEntity("年龄", "age"));
        // entity.add(buildExcelEntityByGroup("U-姓名", "user.name", "用户"));
        // entity.add(buildExcelEntityByGroup("U-年龄", "user.age", "用户"));
        // entity.add(buildExcelEntityByGroup("M-k1", "map.k1", "Map"));
        // entity.add(buildExcelEntityByGroup("M-k2", "map.k2", "Map"));
        entity.add(buildExcelEntityByGroup("M-k1", "map_k1", "Map"));
        entity.add(buildExcelEntityByGroup("M-k2", "map_k2", "Map"));
        return entity;
    }

    public static ExcelExportEntity buildExcelEntity(String name, String key) {
        ExcelExportEntity excelEntity = buildExcelEntityByGroup(name, key, null, -1);
        excelEntity.setWidth(20);
        // try {
        //     String methodStr = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
        //     Method method = DynamicDTO.class.getMethod(methodStr);
        //     excelEntity.setMethod(method);
        // } catch (NoSuchMethodException e) {
        //     throw new RuntimeException(e);
        // }
        return excelEntity;
    }

    public static ExcelExportEntity buildExcelEntityByGroup(String name, Object key, String group) {
        return buildExcelEntityByGroup(name, key, group, -1);
    }

    /**
     * 在 group 列下添加子列
     */
    public static ExcelExportEntity buildExcelEntityByGroup(String name, Object key, String group, int orderNo) {
        ExcelExportEntity excelEntity = new ExcelExportEntity(name, key);
        excelEntity.setWidth(16);
        if (group != null && !group.isEmpty())
            excelEntity.setGroupName(group);
        if (orderNo >= 0)
            excelEntity.setOrderNum(orderNo);
        return excelEntity;
    }

}
