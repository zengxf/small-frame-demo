package com.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * DatabaseSynchronizer的单元测试类
 */
@Slf4j
public class DatabaseSynchronizerTest {
    private static final String SOURCE_URL = "jdbc:mysql://localhost:3306/source_db?useSSL=false&serverTimezone=UTC";
    private static final String S_USERNAME = "src_u";
    private static final String S_PASSWORD = "1122";
    private static final String TARGET_URL = "jdbc:mysql://localhost:3306/target_db?useSSL=false&serverTimezone=UTC";
    private static final String T_USERNAME = "tar_u";
    private static final String T_PASSWORD = "1122";
    // private static final String T_USERNAME = "root";
    // private static final String T_PASSWORD = "1234";

    private DatabaseSynchronizer synchronizer;


    @Before
    public void setUp() {
        // 初始化同步器
        synchronizer = new DatabaseSynchronizer(
                SOURCE_URL, S_USERNAME, S_PASSWORD,
                TARGET_URL, T_USERNAME, T_PASSWORD
        );
    }


    @Test
    public void testSynchronize() {
        synchronizer.synchronizeAll();
    }


    @Test
    public void getTables() {
        try (Connection conn = DriverManager.getConnection(TARGET_URL, T_USERNAME, T_PASSWORD)) {
            List<String> tables = synchronizer.getAllTables(conn);
            log.info("01-tables: {}", tables);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getTableColumns() {
        try (Connection conn = DriverManager.getConnection(TARGET_URL, T_USERNAME, T_PASSWORD)) {
            List<String> tables = synchronizer.getAllTables(conn);
            log.info("02-tables: {}", tables);

            /**
             * 用 root 账号，不是同一个库也能查出来
             */
            String tableName = "test_01";

            DatabaseMetaData metaData = conn.getMetaData();
            Map<String, ColumnInfo> columns = synchronizer.getTableColumns(metaData, tableName);
            log.info("02-columns: {}", columns);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}