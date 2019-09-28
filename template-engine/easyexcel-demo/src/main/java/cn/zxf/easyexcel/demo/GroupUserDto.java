package cn.zxf.easyexcel.demo;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupUserDto {

    @ExcelProperty( "组 ID" )
    @ColumnWidth( 30 )
    private String  id;

    @ExcelProperty( "组名" )
    @ColumnWidth( 30 )
    private String  name;

    @ExcelProperty( "姓名" )
    @ColumnWidth( 20 )
    private String  userName;

    @ExcelProperty( "年龄" )
    private Integer age;

    @ExcelProperty( "创建日期" )
    @ColumnWidth( 30 )
    private Date    createDate;

}
