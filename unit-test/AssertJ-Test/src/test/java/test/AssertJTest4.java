package test;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.data.Index;
import org.junit.Test;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


/**
 * <br>
 * Created by ZXFeng on 2023/7/11
 */
public class AssertJTest4 {

    /*** 测试字符串-正常的 */
    @Test
    public void testStrOk() {
        String name = "I am Mkyong!";
        assertThat(name)
                .as("字符串不对")
                .isEqualTo("I am Mkyong!")
                .isEqualToIgnoringCase("I AM mkyong!")
                .startsWith("I")
                .endsWith("!")
                .containsIgnoringCase("mkyong");
    }

    /*** 测试字符串-有误的 */
    @Test
    public void testStrErr() {
        String name = "I am Mkyong";
        assertThat(name)
                .as("字符串不对")
                // .isEqualTo("I am Mkyong!")
                // .isEqualToIgnoringCase("I AM mkyong!")
                .startsWith("I")
                .endsWith("!")
                .containsIgnoringCase("mkyong");
    }

    /*** 测试集合-正常的 */
    @Test
    public void testListOk() {
        List<String> list = Arrays.asList("Java", "Rust", "Clojure");
        assertThat(list)
                .hasSize(3)
                .contains("Java", "Clojure")
                .contains("Java", Index.atIndex(0))
                .contains("Rust", Index.atIndex(1))
                .contains("Clojure", Index.atIndex(2))
                .doesNotContain("Node JS");
    }

    /*** 测试集合-有误的 */
    @Test
    public void testListErr() {
        List<String> list = Arrays.asList("Java", "Rust", "Clojure");
        assertThat(list)
                .as("集合内容不对")
                // .hasSize(4)
                // .contains("Java-a", "Clojure")
                .contains("Java", Index.atIndex(2))
                .contains("Rust", Index.atIndex(1))
                .contains("Clojure", Index.atIndex(2))
                .doesNotContain("Node JS");
    }

    /*** 测试 Map-正常的 */
    @Test
    public void testMapOk() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "mkyong");

        assertThat(map)
                .hasSize(1)
                .extractingByKey("name", as(InstanceOfAssertFactories.STRING))
                .isEqualToIgnoringCase("mkyong")
                .startsWith("mkyong");

        assertThat(map)
                .extracting("name")
                .isEqualTo("mkyong");

        Map<String, Object> map2 = new HashMap<>();
        map2.put("number", 999);

        assertThat(map2)
                .hasSize(1)
                .extractingByKey("number", as(InstanceOfAssertFactories.INTEGER))
                .isEqualTo(999);
    }

    /*** 测试 Map-有误的 */
    @Test
    public void testMapErr() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zxf");

        assertThat(map)
                .as("Map 内容有误")
                .hasSize(1)
                .extractingByKey("name", as(InstanceOfAssertFactories.STRING))
                .isEqualToIgnoringCase("ZXF")
                .startsWith("xz");

        assertThat(map)
                .extracting("name")
                .isEqualTo("mkyong");
    }

    /*** 测试异常-正常的 */
    @Test
    public void testExceptionOk() {
        assertThatThrownBy(() -> divide(1, 0))
                .isInstanceOf(ArithmeticException.class)
                .hasMessageContaining("zero")
                .hasMessage("/ by zero");

        assertThatThrownBy(() -> Arrays.asList("one", "two").get(2))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageContaining("Index 2 out of bounds");
    }

    /*** 测试异常-有误的 */
    @Test
    public void testExceptionErr() {
        assertThatThrownBy(() -> divide(1, 0))
                .isInstanceOf(ArithmeticException.class)
                .hasMessageContaining("zero")
                .hasMessage("/ by zero");

        assertThatThrownBy(() -> Arrays.asList("one", "two").get(2))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageContaining("Index 3 out of bounds");
    }

    int divide(int input, int divide) {
        return input / divide;
    }

}
