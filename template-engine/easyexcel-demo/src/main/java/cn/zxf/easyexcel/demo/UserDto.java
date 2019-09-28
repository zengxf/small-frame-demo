package cn.zxf.easyexcel.demo;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    @ExcelProperty( "姓名" )
    private String  name;

    @ExcelProperty( "年龄" )
    private Integer age;

    @ExcelProperty( "创建日期" )
    @ColumnWidth( 30 )
    private Date    createDate;

}
