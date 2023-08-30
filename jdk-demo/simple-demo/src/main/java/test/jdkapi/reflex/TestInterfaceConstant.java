package test.jdkapi.reflex;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 测试接口常量更改
 * <br/>
 * Created by ZXFeng on 2022/9/9.
 */
@Slf4j
public class TestInterfaceConstant {

    public static void main(String[] args) throws Exception {
        log.info("INT1: [{}]，INT2: [{}]", Constant.INT1, Constant.INT2);
        Field field1 = Constant.class.getField("INT1");
        Field field2 = Constant.class.getField("INT2");
        Field modifiers = Field.class.getDeclaredField("modifiers");

        log.info("field1: {}", field1);
        log.info("field2: {}", field2);
        log.info("modifiers: {}", modifiers);

        field1.setAccessible(true);
        field2.setAccessible(true);
        modifiers.setAccessible(true);

        modifiers.setInt(field1, field1.getModifiers() & ~Modifier.FINAL); // 设置可改
        modifiers.setInt(field2, field2.getModifiers() & ~Modifier.FINAL); // 设置可改
        field1.setInt(null, 25); // 改变值
        field2.setInt(null, 20); // 改变值
        modifiers.setInt(field1, field1.getModifiers() & ~Modifier.FINAL); // 还原
        modifiers.setInt(field2, field2.getModifiers() & ~Modifier.FINAL); // 还原

        log.info("INT1: [{}]，INT2: [{}]", Constant.INT1, Constant.INT2);
    }

    interface Constant {
        boolean isA = null == null;                             // 防止 JVM 编译时作为常量处理
        int
                INT1 = (isA ? 5 : 0),
                INT2 = 10;
    }

}
