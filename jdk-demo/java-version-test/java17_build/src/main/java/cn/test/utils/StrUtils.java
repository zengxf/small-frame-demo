package cn.test.utils;

import cn.hutool.core.util.StrUtil;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * <p/>
 * Created by ZXFeng on 2024/10/9
 */
public class StrUtils {

    public static String outMsg() {
        String msg = "\n---------------------- \n";
        msg += "Date: " + LocalDate.now() + " \n";
        msg += "Time: " + LocalTime.now() + " \n";
        msg += StrUtil.format("=========> [{}] \n", 888);   // 引入其他包，测试打包大小
        msg += "----------------------";
        return msg;
    }

}
