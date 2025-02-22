package test.captcha;

import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.lang.Console;

/**
 * <p/>
 * ZXF 创建于 2025/2/22
 */
public class TestRandomGenerator {

    public static void main(String[] args) {
        RandomGenerator rg = new RandomGenerator("0123456789ABCDEFHIJKLMN", 4);
        for (int i = 0; i < 10; i++) {
            Console.log("{}: {}", i, rg.generate());
        }
    }


}
