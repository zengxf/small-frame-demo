package test.captcha;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.lang.Console;

/**
 * 生成：图形验证码
 * <p/>
 * ref: https://doc.hutool.cn/pages/captcha/
 * <p/>
 * ZXF 创建于 2025/2/21
 */
public class TestEnNum {

    public static void main(String[] args) {
        testLine();

        testCircle();

        testShear();
    }

    // 扭曲干扰验证码
    private static void testShear() {
        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        //图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write("d:/shear.png");

        //验证图形验证码的有效性，返回boolean值
        captcha.verify("1234");
        Console.log("verify: {}", captcha.verify("1234"));
    }

    // 圆圈干扰验证码
    static void testCircle() {
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
        //图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write("d:/circle.png");

        Console.log(captcha.getCode());
        //验证图形验证码的有效性，返回boolean值
        Console.log("verify: {}", captcha.verify("1234"));
    }

    // 线段干扰的验证码
    static void testLine() {
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);

        //图形验证码写出，可以写出到文件，也可以写出到流
        lineCaptcha.write("d:/line.png");

        //输出code
        Console.log(lineCaptcha.getCode());
        //验证图形验证码的有效性，返回boolean值
        Console.log("verify: {}", lineCaptcha.verify("1234"));


        //重新生成验证码
        lineCaptcha.createCode();
        lineCaptcha.write("d:/line.png");

        //新的验证码
        Console.log(lineCaptcha.getCode());
        //验证图形验证码的有效性，返回boolean值
        lineCaptcha.verify("1234");
        Console.log("verify: {}", lineCaptcha.verify("1234"));
    }

}
