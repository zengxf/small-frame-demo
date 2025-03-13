package com.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * DatabaseSynchronizer的单元测试类
 */
public class DatabaseSynchronizerTest {
    private static final String SOURCE_URL = "jdbc:mysql://localhost:3306/source_db?useSSL=false&serverTimezone=UTC";
    private static final String TARGET_URL = "jdbc:mysql://localhost:3306/target_db?useSSL=false&serverTimezone=UTC";
    // private static final String SOURCE_URL = "jdbc:mysql://localhost:3306/source_db?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=true&allowPublicKeyRetrieval=true";
    // private static final String TARGET_URL = "jdbc:mysql://localhost:3306/target_db?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=true&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "abcd";

    private DatabaseSynchronizer synchronizer;


    @Before
    public void setUp() {
        // 初始化同步器
        synchronizer = new DatabaseSynchronizer(
                SOURCE_URL, USERNAME, PASSWORD,
                TARGET_URL, USERNAME, PASSWORD
        );
    }


    @Test
    public void testSynchronize() {
        synchronizer.synchronizeAll();
    }


}