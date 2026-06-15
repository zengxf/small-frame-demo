package com.fesod.sheet.util;

import org.apache.fesod.sheet.FesodSheet;
import org.apache.fesod.sheet.context.AnalysisContext;
import org.apache.fesod.sheet.read.listener.ReadListener;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Excel 读写工具类（基于 Apache FesodSheet）
 */
public final class ExcelParserUtil {

    private ExcelParserUtil() {}

    // ============ 从 InputStream 流式解析 ============

    /**
     * 从 InputStream 流式解析 Excel，每行回调一次。
     * 基于 SAX 事件驱动，不会将整个文件加载到内存。
     */
    public static <T> void parse(InputStream is, Class<T> clazz, Consumer<T> consumer) {
        FesodSheet.read(is, clazz, new ReadListener<T>() {
            @Override
            public void invoke(T data, AnalysisContext context) {
                consumer.accept(data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                // 全部解析完成
            }
        }).sheet().headRowNumber(1).doRead();
    }

    /**
     * 从 InputStream 流式解析，返回完整 List。
     * 注意：仅适用于小数据量，大数据请用上面的回调方式。
     */
    public static <T> List<T> parseAll(InputStream is, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        parse(is, clazz, result::add);
        return result;
    }

    // ============ 从 File 解析 ============

    public static <T> void parse(File file, Class<T> clazz, Consumer<T> consumer) {
        FesodSheet.read(file, clazz, new ReadListener<T>() {
            @Override
            public void invoke(T data, AnalysisContext context) {
                consumer.accept(data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
        }).sheet().headRowNumber(1).doRead();
    }

    public static <T> List<T> parseAll(File file, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        parse(file, clazz, result::add);
        return result;
    }

    // ============ 从文件路径解析 ============

    public static <T> void parse(String filePath, Class<T> clazz, Consumer<T> consumer) {
        FesodSheet.read(filePath, clazz, new ReadListener<T>() {
            @Override
            public void invoke(T data, AnalysisContext context) {
                consumer.accept(data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
        }).sheet().headRowNumber(1).doRead();
    }

    public static <T> List<T> parseAll(String filePath, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        parse(filePath, clazz, result::add);
        return result;
    }

    // ============ 写入 Excel ============

    /**
     * 将实体集合写入 Excel 文件。
     *
     * @param file      目标文件
     * @param clazz     实体类型（注解驱动表头）
     * @param sheetName Sheet 名称
     * @param dataList  数据集合
     */
    public static <T> void write(File file, Class<T> clazz, String sheetName, List<T> dataList) {
        FesodSheet.write(file, clazz).sheet(sheetName).doWrite(dataList);
    }

    /**
     * 将实体集合写入 Excel 文件。
     *
     * @param filePath  目标文件路径
     * @param clazz     实体类型
     * @param sheetName Sheet 名称
     * @param dataList  数据集合
     */
    public static <T> void write(String filePath, Class<T> clazz, String sheetName, List<T> dataList) {
        FesodSheet.write(filePath, clazz).sheet(sheetName).doWrite(dataList);
    }

}
