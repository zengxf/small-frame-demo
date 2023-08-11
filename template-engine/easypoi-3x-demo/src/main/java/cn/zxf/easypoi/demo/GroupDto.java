package cn.zxf.easypoi.demo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class GroupDto {

    @Excel(name = "G-ID", width = 20)
    private String id;
    @Excel(name = "G-name", width = 20)
    private String name;
    @ExcelCollection(name = "G-users")
    private List<UserDto> users;

}
