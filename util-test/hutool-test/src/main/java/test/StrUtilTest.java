package test;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Map;

/**
 * <br/>
 * Created by ZXFeng on 2021/12/28.
 */
public class StrUtilTest {

    public static void main(String[] args) {
        String fmt = "{name} != null && {name} >= {value}";
        Map<String, Object> map = Map.of("name", "age", "value", 33);
        String str = StrUtil.format(fmt, map);
        System.out.println(StrUtil.format("fmt: [{}], map: [{}]", fmt, map));
        System.out.println(StrUtil.format("str: [{}]", str));


        String pwd = "123456abc890";
        System.out.println(DesensitizedUtil.idCardNum(pwd, 3, 3)); // 数据脱敏
    }

}
