package test.jvm.class_structure;

import java.io.IOException;

/**
 * 测试异常处理表布局
 * <p>
 * 可用 jclasslib 查看。<br>
 * 抛异常时从异常表中查看，如果表中的 from, to 对应上，则跳到对应的 target 上处理；<br>
 * 如果没有找到，则直接抛出到上层调用函数，上层同样的操作，没找到则继续抛出
 * 
 * <p>
 * Created by zengxf on 2018-03-09
 */
public class TestExceptionTable {

    static void test() {
        commonOperate();
        try {
            commonOperate();
            commonOperate();
            throwException();
            commonOperate();
            commonOperate();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        commonOperate();
        commonOperate();
        try {
            commonOperate();
            commonOperate();
            throwIOException();
            commonOperate();
            commonOperate();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    static void commonOperate() {
    }

    static void throwException() throws Exception {
        throw new Exception();
    }

    static void throwIOException() throws IOException {
        throw new IOException();
    }

}
