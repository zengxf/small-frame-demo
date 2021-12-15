package test_java16;

/**
 * Java 13 引入 yield 关键字到 Switch 中。 <br/>
 * Switch 表达式中就多了一个关键字用于跳出 Switch 块的关键字 yield，主要用于返回一个值。 <br/>
 * yield 和 return 的区别在于：return 会直接跳出当前循环或者方法，而 yield 只会跳出当前 Switch 块，
 * 同时在使用 yield 时，需要有 default 条件。
 * <p>
 * 命令测试：
 * <pre>
 *     java src/main/java/test_java16/TestSwitchYield.java
 * </pre>
 * Created by zengxf on 2021/3/30.
 */
public class TestSwitchYield {

    public static void main(String[] arr) {
        System.out.println(descLanguage("Java"));
    }

    static String descLanguage(String name) {
        String value = switch (name) {
            case "Java":
                yield "object-oriented, platform independent and secured";
            case "Ruby", "C#": {
                System.out.println("name: " + name);
                yield "a programmer's best friend"; // 用于多行
            }
            default:
				yield name + " is a good language";
        };
        return value;
    }

}
