package test.jdkapi.regexp;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

/**
 * 恶意的正则表达式模式
 *
 * <pre>
 * 每个恶意的正则表达式模式应该包含：
 *      使用重复分组构造
 *      在重复组内会出现
 *          重复
 *          交替重叠
 *    有缺陷的正则表达式会包含如下部分：
 *      (a+)+
 *      ([a-zA-Z]+)*
 *      (a|aa)+
 *      (a|a?)+
 *      (.*a){x} | for x > 10
 *      注意: 这里的a是个泛指
 * </pre>
 * <p>
 * 实例-缺陷正则
 *
 * <pre>
 *      英文的个人名字:
 *      Regex: ^[a-zA-Z]+(([\'\,\.\-][a-zA-Z ])?[a-zA-Z]*)*$
 *      Payload: aaaaaaaaaaaaaaaaaaaaaaaaaaaa!
 *
 *  Java Classname
 *      Regex: ^(([a-z])+.)+[A-Z]([a-z])+$
 *      Payload: aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!
 *
 *  Email格式验证
 *      Regex: ^([0-9a-zA-Z]([-.\w]*[0-9a-zA-Z])*@(([0-9a-zA-Z])+([-\w]*[0-9a-zA-Z])*\.)+[a-zA-Z]{2,9})$
 *      Payload: a@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!
 *
 *      多个邮箱地址验证
 *      Regex: ^[a-zA-Z]+(([\'\,\.\-][a-zA-Z ])?[a-zA-Z]*)*\s+&lt;(\w[-._\w]*\w@\w[-._\w]*\w\.\w{2,3})&gt;$|^(\w[-._\w]*\w@\w[-._\w]*\w\.\w{2,3})$
 *      Payload: aaaaaaaaaaaaaaaaaaaaaaaa!
 *
 *     复数验证
 *      Regex: ^\d*[0-9](|.\d*[0-9]|)*$
 *      Payload: 1111111111111111111111111!
 * </pre>
 * <p>
 * 防范手段
 *
 * <pre>
 *  降低正则表达式的复杂度, 尽量少用分组
 *      严格限制用户输入的字符串长度(特定情况下)
 *      使用单元测试、fuzzing 测试保证安全
 *      使用静态代码分析工具, 如: sonar
 *      添加服务器性能监控系统, 如: zabbix
 * </pre>
 *
 * <p>
 * Created by zengxf on 2018-01-25
 */
@Slf4j
public class TestRedos {

    public static void main(String[] args) {
        String regex = "^(a+)+$";
        Pattern p = Pattern.compile(regex);
        String[] msgs = { //
                "aaaaaaaaaaaaaaaaX" // 2^16
                , "aaaaaaaaaaaaaaaaaaX" // 2^18
                , "aaaaaaaaaaaaaaaaaaaaX" // 2^20
                , "aaaaaaaaaaaaaaaaaaaaaaX" // 2^22
                , "aaaaaaaaaaaaaaaaaaaaaaaaX" // 2^24
                , "aaaaaaaaaaaaaaaaaaaaaaaaaaX" // 2^26
                // , "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaX" // 2^36
        };
        Stream.of(msgs).forEach(msg -> {
            long start = System.nanoTime();
            p.matcher(msg).matches();
            long ns = System.nanoTime() - start;
            log.info("msg: {}, use time: {} ms {} ns", msg, ns / 1000_000, ns % 1000_000);
        });
    }

}
