package cn.zxf.easypoi.demo;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import java.util.stream.IntStream;

/**
 * 嵌套动态列
 * <br>
 * ref: https://blog.csdn.net/z28126308/article/details/100079790
 * <br>
 * Created by ZXFeng on 2023/8/10
 */
public class DynamicMapTest extends DataUtils {

    public static void main(String[] args) throws IOException {
        String filePath = "D:\\Data\\test\\excel-test\\ExcelExportForMap.xlsx";

        List<ExcelExportEntity> entity = exportEntities(); // 设置头与数据绑定关系
        List<Map<String, Object>> list = getMapData();

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("测试", "测试"), entity, list);
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }
    }

    private static List<Map<String, Object>> getMapData() {
        List<Map<String, Object>> list = new ArrayList<>();
        IntStream.range(0, 10).forEach(i -> {
            Map<String, Object> params = new HashMap<>();
            params.put("order", i);
            params.put("sex", "sex" + i);
            params.put("class", "class-" + i);
            params.put("students.sex", "man-" + i);
            params.put("students.name", "name-" + i);
            list.add(params);
        });
        return list;
    }

    private static List<ExcelExportEntity> exportEntities() {
        List<ExcelExportEntity> entity = new ArrayList<>();
        // entity.add(buildExcelEntity("序号", "order", 0));
        // entity.add(buildExcelEntity("性别", "sex", 1));
        // // 设置了 group 的若不设置排序默认会派到最后
        // entity.add(buildExcelEntityByGroup("姓名", "students.name", "学生", 2));
        // entity.add(buildExcelEntityByGroup("性别", "students.sex", "学生", 2));
        // entity.add(buildExcelEntity("班级", "class", 3));
        // --------------
        entity.add(buildExcelEntity("序号", "order"));
        entity.add(buildExcelEntity("性别", "sex"));
        entity.add(buildExcelEntity("班级", "class"));
        entity.add(buildExcelEntityByGroup("姓名", "students.name", "学生"));
        entity.add(buildExcelEntityByGroup("性别", "students.sex", "学生"));
        return entity;
    }

}
