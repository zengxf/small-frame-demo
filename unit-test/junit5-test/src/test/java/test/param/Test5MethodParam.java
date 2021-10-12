package test.param;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

/**
 * JUnit 5 参数化测试。
 * 参考：https://blog.csdn.net/ryo1060732496/article/details/80823696
 * <br/>
 * Created by ZXFeng on  2021/10/12.
 */
@Slf4j
@DisplayName("参数化测试")
public class Test5MethodParam {

    static List<DataItem> getStaticDataList() {
        log.info("---------");
        return List.of(
                new DataItem("test1", "1111"),
                new DataItem("test2", "2222")
        );
    }

    static List<DataItem> testStaticAutoMatch() {
        log.info("---------");
        return getStaticDataList();
    }

    List<DataItem> getInstanceDataList() {
        log.info("---------");
        return List.of(
                new DataItem("test1", "1111"),
                new DataItem("test2", "2222")
        );
    }

    @DisplayName("静态方法返回集合，用此集合中每个元素作为入参")
    @ParameterizedTest
    @MethodSource("getStaticDataList")
    public void testStatic(DataItem item) {
        log.info("item: [{}]", item);
    }

    @DisplayName("静态方法(自动匹配)返回集合，用此集合中每个元素作为入参")
    @ParameterizedTest
    @MethodSource
    public void testStaticAutoMatch(DataItem item) {
        log.info("item: [{}]", item);
    }

    @DisplayName("自定义类返回集合，用此集合中每个元素作为入参")
    @ParameterizedTest
    @ArgumentsSource(MyArgumentsProvider.class)
    public void testClass(DataItem item) {
        log.info("item: [{}]", item);
    }

    @Disabled("实例方法不行，报错!!!")
    @DisplayName("实例方法返回集合，用此集合中每个元素作为入参")
    @ParameterizedTest
    @MethodSource("getInstanceDataList")
    public void testInstance(DataItem item) {
        log.info("item: [{}]", item);
    }

}
