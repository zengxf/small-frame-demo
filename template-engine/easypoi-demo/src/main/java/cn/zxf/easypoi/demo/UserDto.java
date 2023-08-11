package cn.zxf.easypoi.demo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

@Data
@Accessors(chain = true)
public class UserDto {

    @Excel(name = "姓名-sub", width = 20)
    private String name;

    @Excel(name = "年龄-sub", width = 20)
    private Integer age;

    @Excel(name = "时间-sub", width = 20, format = "yyyy-MM-dd")
    private Date createDate;
    @Excel(name = "时间2-sub", width = 20, format = "yyyy-MM-dd")
    private LocalDate createDate2;

}
