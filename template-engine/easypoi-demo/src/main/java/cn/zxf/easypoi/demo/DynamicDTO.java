package cn.zxf.easypoi.demo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * <br>
 * Created by ZXFeng on 2023/8/10
 */
@Data
@Accessors(chain = true)
public class DynamicDTO {

    @Excel(name = "姓名", width = 12)
    private String name;

    @Excel(name = "年龄", width = 12)
    private String age;

    @ExcelEntity(name = "动态")
    private Map<String, String> dynamic;

}
