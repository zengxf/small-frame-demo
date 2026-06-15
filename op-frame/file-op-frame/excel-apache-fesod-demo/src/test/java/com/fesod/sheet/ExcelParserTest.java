package com.fesod.sheet;

import com.fesod.sheet.model.UserInfo;
import com.fesod.sheet.util.ExcelParserUtil;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ExcelParserUtil 单元测试 — 模拟解析"用户信息表"
 * <p>读写均使用固定文件 D:\\_Data\\temp\\test-001.xlsx</p>
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExcelParserTest {

    private static final File TEST_FILE = new File("D:\\_Data\\temp\\test-001.xlsx");
    private static final int COUNT = 50;

    @BeforeAll
    static void setUp() throws Exception {
        // 确保父目录存在
        File parent = TEST_FILE.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        // 构造模拟数据
        List<UserInfo> mockData = new ArrayList<>();
        long base = 1704067200000L; // 2024-01-01
        for (int i = 1; i <= COUNT; i++) {
            UserInfo u = new UserInfo();
            u.setUserId("U" + String.format("%05d", i));
            u.setUserName("用户" + i);
            u.setPhone("138" + String.format("%08d", i));
            u.setHireDate(new Date(base + (i - 1) * 7L * 86400_000));
            mockData.add(u);
        }

        // 写入固定文件
        ExcelParserUtil.write(TEST_FILE, UserInfo.class, "用户信息", mockData);
        System.out.println("测试文件已生成: " + TEST_FILE.getAbsolutePath());
    }

    @AfterAll
    static void tearDown() throws Exception {
        // Files.deleteIfExists(TEST_FILE.toPath());
        System.out.println("测试文件不清理: " + TEST_FILE.getAbsolutePath());
    }

    // ========== 流式回调解析 ==========

    @Test
    @Order(10)
    void testStreamParse() throws Exception {
        List<UserInfo> all = new ArrayList<>();

        try (InputStream is = Files.newInputStream(TEST_FILE.toPath())) {
            ExcelParserUtil.parse(is, UserInfo.class, all::add);
        }

        assertEquals(COUNT, all.size());
        assertEquals("U00001", all.get(0).getUserId());
        assertEquals("用户1", all.get(0).getUserName());
        assertNotNull(all.get(0).getHireDate());
        System.out.println("流式解析完成，共 " + all.size() + " 条");
    }

    // ========== 全量解析（小数据量场景） ==========

    @Test
    @Order(20)
    void testParseAll() {
        List<UserInfo> all = ExcelParserUtil.parseAll(TEST_FILE, UserInfo.class);

        assertEquals(COUNT, all.size());
        // 打印前 5 条
        all.stream().limit(5).forEach(System.out::println);
    }

    // ========== 从文件路径字符串解析 ==========

    @Test
    @Order(3)
    void testParseByPath() {
        List<UserInfo> all = ExcelParserUtil.parseAll(
                TEST_FILE.getAbsolutePath(), UserInfo.class);
        assertEquals(COUNT, all.size());
        System.out.println("路径解析完成，共 " + all.size() + " 条");
    }

    // ========== 写入 Excel（覆盖同一文件） ==========

    @Test
    @Order(4)
    void testWrite() {
        // 构造 10 条新数据
        List<UserInfo> data = new ArrayList<>();
        long base = 1704067200000L;
        for (int i = 1; i <= 10; i++) {
            UserInfo u = new UserInfo();
            u.setUserId("W" + String.format("%05d", i));
            u.setUserName("写入用户" + i);
            u.setPhone("139" + String.format("%08d", i));
            u.setHireDate(new Date(base + i * 30L * 86400_000));
            data.add(u);
        }

        // 覆盖写入同一文件
        ExcelParserUtil.write(TEST_FILE, UserInfo.class, "写入测试", data);
        assertTrue(TEST_FILE.exists() && TEST_FILE.length() > 0, "写入后文件应存在且大小 > 0");
        System.out.println("Excel 文件已写入: " + TEST_FILE.getAbsolutePath());

        // 回读验证
        List<UserInfo> readBack = ExcelParserUtil.parseAll(TEST_FILE, UserInfo.class);
        assertEquals(10, readBack.size(), "回读条数应为 10");
        assertEquals("W00001", readBack.get(0).getUserId());
        assertEquals("写入用户1", readBack.get(0).getUserName());
        System.out.println("回读验证通过，共 " + readBack.size() + " 条");
    }

    // ========== 异常场景 ==========

    @Test
    @Order(5)
    void testFileNotExist() {
        assertThrows(Exception.class, () ->
                ExcelParserUtil.parseAll(new File("no-such-file.xlsx"), UserInfo.class));
    }
}
