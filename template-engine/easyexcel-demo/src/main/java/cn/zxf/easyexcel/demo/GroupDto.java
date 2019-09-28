package cn.zxf.easyexcel.demo;

import java.util.List;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupDto {

    @ExcelProperty( "组 ID" )
    @ColumnWidth( 30 )
    private String        id;

    @ExcelProperty( "组名" )
    @ColumnWidth( 30 )
    private String        name;

    @ExcelProperty( "用户集合" )
    @ColumnWidth( 30 )
    private List<UserDto> users;

}
