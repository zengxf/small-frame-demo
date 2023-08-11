package cn.zxf.easypoi.demo;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

/**
 * <br>
 * Created by ZXFeng on 2023/8/11
 */
public class DataUtils {

    public static List<DynamicDTO> getData() {
        List<DynamicDTO> list = new ArrayList<>();
        IntStream.range(1, 10).forEach(i -> {
            Map<String, String> map = new HashMap<>();
            map.put("k1", "V1-" + i);
            map.put("k2", "V2-" + i);
            map.put("k3", "V3-" + i);

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

    public static List<ExcelExportEntity> dtoExportEntities() {
        List<ExcelExportEntity> entity = new ArrayList<>();
        entity.add(buildExcelEntity("姓名", "name"));
        entity.add(buildExcelEntity("年龄", "age"));
        entity.add(buildExcelEntityByGroup("U-姓名", "user.name", "用户"));
        entity.add(buildExcelEntityByGroup("U-年龄", "user.age", "用户"));
        entity.add(buildExcelEntityByGroup("M-k1", "map.k1", "Map"));
        entity.add(buildExcelEntityByGroup("M-k2", "map.k2", "Map"));
        return entity;
    }

    public static ExcelExportEntity buildExcelEntity(String name, Object key) {
        ExcelExportEntity excelEntity = buildExcelEntityByGroup(name, key, null, -1);
        excelEntity.setWidth(20);
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
        if (group != null && !group.isBlank())
            excelEntity.setGroupName(group);
        if (orderNo >= 0)
            excelEntity.setOrderNum(orderNo);
        return excelEntity;
    }

}
