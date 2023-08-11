package cn.zxf.easypoi.demo;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * <br>
 * Created by ZXFeng on 2023/8/10
 */
public class DynamicDTOMapTest extends DataUtils {

    public static void main(String[] args) throws IOException {
        String filePath = "D:\\Data\\test\\excel-test\\ExcelExportForDTO-Map.xlsx";

        List<DynamicDTO> list = getData();
        List<ExcelExportEntity> entity = dtoExportEntities(); // 设置头与数据绑定关系

        ExportParams params = new ExportParams("test-0000-8888", "测试");
        Workbook workbook = ExcelExportUtil.exportExcel(params, entity, list);
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }
    }

}
