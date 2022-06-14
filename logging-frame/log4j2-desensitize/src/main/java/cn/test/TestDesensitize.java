package cn.test;

import lombok.extern.slf4j.Slf4j;

/**
 * <br/>
 * Created by ZXFeng on 2022/6/14.
 */
@Slf4j
public class TestDesensitize {

    public static void main(String[] args) {
        User user = new User();
        user.setCardNo("6227002020000101222");
        user.setTel("0571-28821111");
        user.setAddress("浙江省西湖区西湖路288号钱江乐园2-101室");
        user.setEmail("zhangs12345@qq.com");
        user.setPassword("123456");
        user.setMobile("15911116789");
        user.setName("张三");
        user.setIdCard("360123202111111122");

        log.info("\n\n User: [{}]", user);
    }

}
