package com.fesod.sheet.model;

import lombok.Data;
import org.apache.fesod.sheet.annotation.ExcelProperty;
import org.apache.fesod.sheet.annotation.format.DateTimeFormat;

import java.util.Date;

/**
 * 用户信息表实体（用户ID、用户名、手机号、入职日期）
 */
@Data
public class UserInfo {

    @ExcelProperty("用户ID")
    private String userId;

    @ExcelProperty("用户名")
    private String userName;

    @ExcelProperty("手机号")
    private String phone;

    @ExcelProperty("入职日期")
    @DateTimeFormat("yyyy-MM-dd")
    private Date hireDate;
    
}
