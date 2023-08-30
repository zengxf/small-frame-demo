package test.lang_features;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试隐藏
 * <br/>
 * 隐藏：只针对属性和同签静态方法有效
 * <br/>
 * Created by ZXFeng on  2021/11/3.
 */
@Slf4j
public class TestHide {

    public static void main(String[] args) {
        Child cObj = new Child();
        log.info("cObj-sign: [{}]", cObj.sign);
        log.info("cObj-SIGN: [{}]", cObj.SIGN);
        cObj.print();

        log.info("-------------");
        log.info("-------------");

        Parent pObj = cObj;
        log.info("pObj-sign: [{}]", pObj.sign);
        log.info("pObj-SIGN: [{}]", pObj.SIGN);
        pObj.print();
    }

    public static class Parent {
        static String SIGN = "父001";
        String sign = "父001";

        public static void print() {
            log.info("parent-sign: [{}]", SIGN);
        }
    }

    public static class Child extends Parent {
        static int SIGN = 100;
        int sign = 100;

        public static void print() {
            log.info("child-sign: [{}]", SIGN);
        }

        // 不兼容
//        public static int print() {
//            log.info("parent-sign: [{}]", SIGN);
//            return SIGN;
//        }
    }

}
