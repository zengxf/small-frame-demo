package com.example;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库表结构同步工具类
 * 用于将源数据库的表结构同步到目标数据库
 * 支持以下功能：
 * 1. 如果目标数据库中不存在该表，则创建表
 * 2. 如果表存在但缺少字段，则添加相应字段
 * 3. 如果字段存在但类型不匹配，则修改字段类型
 * 4. 同步字段的可空性、默认值等属性
 * 5. 支持同步整个数据库的表结构
 */
@Slf4j
public class DatabaseSynchronizer {

    private final String sourceUrl;
    private final String sourceUsername;
    private final String sourcePassword;
    private final String targetUrl;
    private final String targetUsername;
    private final String targetPassword;

    /**
     * 构造函数
     *
     * @param sourceUrl      源数据库URL
     * @param sourceUsername 源数据库用户名
     * @param sourcePassword 源数据库密码
     * @param targetUrl      目标数据库URL
     * @param targetUsername 目标数据库用户名
     * @param targetPassword 目标数据库密码
     */
    public DatabaseSynchronizer(
            String sourceUrl, String sourceUsername, String sourcePassword,
            String targetUrl, String targetUsername, String targetPassword
    ) {
        this.sourceUrl = sourceUrl;
        this.sourceUsername = sourceUsername;
        this.sourcePassword = sourcePassword;
        this.targetUrl = targetUrl;
        this.targetUsername = targetUsername;
        this.targetPassword = targetPassword;
    }

    /**
     * 同步整个数据库的表结构
     * 将源数据库中的所有表结构同步到目标数据库
     */
    public void synchronizeAll() {
        try (Connection sourceConn = DriverManager.getConnection(sourceUrl, sourceUsername, sourcePassword)) {
            List<String> tables = getAllTables(sourceConn);

            if (tables.isEmpty()) {
                log.warn("No tables found in source database");
                return;
            }

            log.info("Found {} tables in source database", tables.size());

            for (String tableName : tables) {
                try {
                    synchronize(tableName);
                    log.info("Synchronized table: {}", tableName);
                } catch (Exception e) {
                    log.error("Failed to synchronize table '{}': {}", tableName, e.getMessage(), e);
                }
            }

            log.info("Database synchronization completed. Total tables: {}", tables.size());
        } catch (SQLException e) {
            log.error("Error connecting to source database: " + e.getMessage(), e);
        }
    }

    /**
     * 获取数据库中所有表名
     *
     * @param conn 数据库连接
     * @return 表名列表
     * @throws SQLException 如果发生SQL异常
     */
    private List<String> getAllTables(Connection conn) throws SQLException {
        List<String> tables = new ArrayList<>();
        DatabaseMetaData metaData = conn.getMetaData();

        // 获取用户表（排除系统表）
        try (ResultSet rs = metaData.getTables(conn.getCatalog(), null, "%", new String[]{"TABLE"})) {
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                tables.add(tableName);
            }
        }

        return tables;
    }

    /**
     * 同步指定表的结构
     *
     * @param tableName 要同步的表名
     */
    public void synchronize(String tableName) {
        try (Connection sourceConn = DriverManager.getConnection(sourceUrl, sourceUsername, sourcePassword);
             Connection targetConn = DriverManager.getConnection(targetUrl, targetUsername, targetPassword)) {

            DatabaseMetaData sourceMetaData = sourceConn.getMetaData();
            DatabaseMetaData targetMetaData = targetConn.getMetaData();

            // 获取源表的列信息
            Map<String, ColumnInfo> sourceColumns = getTableColumns(sourceMetaData, tableName);
            if (sourceColumns.isEmpty()) {
                log.error("Source table '{}' does not exist", tableName);
                return;
            }

            // 获取目标表的列信息
            Map<String, ColumnInfo> targetColumns = getTableColumns(targetMetaData, tableName);
            if (targetColumns.isEmpty()) {
                // 目标表不存在，创建表
                createTable(targetConn, tableName, sourceColumns);
            } else {
                // 目标表存在，同步列
                synchronizeColumns(targetConn, tableName, sourceColumns, targetColumns);
            }

            log.info("Table '{}' structure synchronized successfully", tableName);

        } catch (SQLException e) {
            log.error("Error synchronizing table structure: " + e.getMessage(), e);
        }
    }

    /**
     * 获取表的所有列信息
     *
     * @param metaData  数据库元数据
     * @param tableName 表名
     * @return 列信息映射，key为列名（小写），value为列信息对象
     * @throws SQLException 如果发生SQL异常
     */
    private Map<String, ColumnInfo> getTableColumns(DatabaseMetaData metaData, String tableName) throws SQLException {
        Map<String, ColumnInfo> columns = new LinkedHashMap<>();

        try (ResultSet rs = metaData.getColumns(null, null, tableName, null)) {
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                int dataType = rs.getInt("DATA_TYPE");
                String typeName = rs.getString("TYPE_NAME");
                int columnSize = rs.getInt("COLUMN_SIZE");
                int decimalDigits = rs.getInt("DECIMAL_DIGITS");
                boolean nullable = rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable;
                String defaultValue = rs.getString("COLUMN_DEF");
                String remarks = rs.getString("REMARKS"); // 获取列注释

                ColumnInfo columnInfo = new ColumnInfo(columnName, dataType, typeName,
                        columnSize, decimalDigits, nullable, defaultValue, remarks);
                columns.put(columnName.toLowerCase(), columnInfo);
            }
        }

        return columns;
    }

    /**
     * 创建表
     *
     * @param conn      数据库连接
     * @param tableName 表名
     * @param columns   列信息
     * @throws SQLException 如果发生SQL异常
     */
    private void createTable(Connection conn, String tableName, Map<String, ColumnInfo> columns) throws SQLException {
        StringBuilder sql = new StringBuilder("CREATE TABLE " + tableName + " (");
        boolean first = true;

        for (ColumnInfo column : columns.values()) {
            if (!first) {
                sql.append(", ");
            }
            sql.append(buildColumnDefinition(column));
            first = false;
        }

        sql.append(")");

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql.toString());
            log.info("Created table '{}'", tableName);

            // 添加列注释
            for (ColumnInfo column : columns.values()) {
                if (column.getRemarks() != null && !column.getRemarks().isEmpty()) {
                    String commentSql = String.format("ALTER TABLE %s MODIFY COLUMN %s %s COMMENT '%s'",
                            tableName, column.getName(), column.getTypeName(), column.getRemarks());
                    stmt.execute(commentSql);
                }
            }
        }
    }

    /**
     * 同步列
     *
     * @param conn          数据库连接
     * @param tableName     表名
     * @param sourceColumns 源表列信息
     * @param targetColumns 目标表列信息
     * @throws SQLException 如果发生SQL异常
     */
    private void synchronizeColumns(Connection conn, String tableName,
                                    Map<String, ColumnInfo> sourceColumns,
                                    Map<String, ColumnInfo> targetColumns) throws SQLException {
        for (Map.Entry<String, ColumnInfo> entry : sourceColumns.entrySet()) {
            String columnName = entry.getKey();
            ColumnInfo sourceColumn = entry.getValue();
            ColumnInfo targetColumn = targetColumns.get(columnName);

            if (targetColumn == null) {
                // 目标表缺少该列，添加列
                addColumn(conn, tableName, sourceColumn);
            } else if (!columnsMatch(sourceColumn, targetColumn)) {
                // 列存在但定义不匹配，修改列
                modifyColumn(conn, tableName, sourceColumn);
            }
        }
    }

    /**
     * 添加列
     *
     * @param conn      数据库连接
     * @param tableName 表名
     * @param column    列信息
     * @throws SQLException 如果发生SQL异常
     */
    private void addColumn(Connection conn, String tableName, ColumnInfo column) throws SQLException {
        String sql = String.format("ALTER TABLE %s ADD COLUMN %s", tableName, buildColumnDefinition(column));

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            log.info("Added column '{}' to table '{}'", column.getName(), tableName);

            // 添加列注释
            if (column.getRemarks() != null && !column.getRemarks().isEmpty()) {
                String commentSql = String.format("ALTER TABLE %s MODIFY COLUMN %s %s COMMENT '%s'",
                        tableName, column.getName(), column.getTypeName(), column.getRemarks());
                stmt.execute(commentSql);
            }
        }
    }

    /**
     * 修改列
     *
     * @param conn      数据库连接
     * @param tableName 表名
     * @param column    列信息
     * @throws SQLException 如果发生SQL异常
     */
    private void modifyColumn(Connection conn, String tableName, ColumnInfo column) throws SQLException {
        String sql = String.format("ALTER TABLE %s MODIFY COLUMN %s", tableName, buildColumnDefinition(column));

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            log.info("Modified column '{}' in table '{}'", column.getName(), tableName);
        }
    }

    /**
     * 构建列定义SQL
     *
     * @param column 列信息
     * @return 列定义SQL
     */
    private String buildColumnDefinition(ColumnInfo column) {
        StringBuilder def = new StringBuilder(column.getName())
                .append(" ")
                .append(column.getTypeName());

        // 添加长度或精度定义
        if (column.getColumnSize() > 0) {
            if (column.getDecimalDigits() > 0) {
                def.append("(")
                        .append(column.getColumnSize())
                        .append(",")
                        .append(column.getDecimalDigits())
                        .append(")");
            } else {
                def.append("(")
                        .append(column.getColumnSize())
                        .append(")");
            }
        }

        // 添加是否可空定义
        if (!column.isNullable()) {
            def.append(" NOT NULL");
        }

        // 添加默认值定义
        if (column.getDefaultValue() != null) {
            def.append(" DEFAULT ")
                    .append(column.getDefaultValue());
        }

        // 添加注释
        if (column.getRemarks() != null && !column.getRemarks().isEmpty()) {
            def.append(" COMMENT '")
                    .append(column.getRemarks())
                    .append("'");
        }

        return def.toString();
    }

    /**
     * 比较两个列的定义是否匹配
     *
     * @param source 源列信息
     * @param target 目标列信息
     * @return 如果列定义完全匹配返回true，否则返回false
     */
    private boolean columnsMatch(ColumnInfo source, ColumnInfo target) {
        return source.getDataType() == target.getDataType() &&
                source.getColumnSize() == target.getColumnSize() &&
                source.getDecimalDigits() == target.getDecimalDigits() &&
                source.isNullable() == target.isNullable() &&
                ((source.getDefaultValue() == null && target.getDefaultValue() == null) ||
                        (source.getDefaultValue() != null && source.getDefaultValue().equals(target.getDefaultValue()))) &&
                ((source.getRemarks() == null && target.getRemarks() == null) ||
                        (source.getRemarks() != null && source.getRemarks().equals(target.getRemarks())));
    }
}