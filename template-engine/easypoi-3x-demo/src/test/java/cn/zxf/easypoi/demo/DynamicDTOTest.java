package cn.zxf.easypoi.demo;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * <br>
 * Created by ZXFeng on 2023/8/10
 */
public class DynamicDTOTest extends DataUtils {

    public static void main(String[] args) throws IOException {
        String filePath = "D:\\Data\\test\\excel-test\\ExcelExportFor-DTO.xlsx";

        List<DynamicDTO> list = getData();

        ExportParams params = new ExportParams("test-0000-8888", "测试");
        Workbook workbook = ExcelExportUtil.exportExcel(params, DynamicDTO.class, list);
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }
    }

}
