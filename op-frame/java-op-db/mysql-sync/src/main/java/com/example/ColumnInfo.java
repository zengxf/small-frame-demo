package com.example;

import lombok.Data;

/**
 * 数据库列信息类
 * 用于存储数据库表中列的详细信息
 */
@Data
public class ColumnInfo {

    private final String name;
    private final int dataType;
    private final String typeName;
    private final int columnSize;
    private final int decimalDigits;
    private final boolean nullable;
    private final String defaultValue;
    private final String remarks;

    /**
     * 构造函数
     *
     * @param name          列名
     * @param dataType      JDBC数据类型（java.sql.Types中定义的类型）
     * @param typeName      数据库特定的类型名称
     * @param columnSize    列大小
     * @param decimalDigits 小数位数（对于数值类型）
     * @param nullable      是否可为空
     * @param defaultValue  默认值
     * @param remarks       列注释
     */
    public ColumnInfo(
            String name, int dataType, String typeName, int columnSize,
            int decimalDigits, boolean nullable, String defaultValue, String remarks
    ) {
        this.name = name;
        this.dataType = dataType;
        this.typeName = typeName;
        this.columnSize = columnSize;
        this.decimalDigits = decimalDigits;
        this.nullable = nullable;
        this.defaultValue = defaultValue;
        this.remarks = remarks;
    }

}