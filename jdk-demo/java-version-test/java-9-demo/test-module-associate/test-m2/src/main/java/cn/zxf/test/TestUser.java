package cn.zxf.test;


import cn.zxf.test_user.User;

import java.util.stream.Stream;

public class TestUser {

    public static void main(String[] args) {
        System.out.println(TestUser.class.getModule());

        User user = new User();
        user.setAge(32).setName("zxf");
        System.out.println(user);

        User.class.getModule().addOpens("cn.zxf.test_user", TestUser.class.getModule()); // 不能反射添加

        Stream.of(User.class.getDeclaredFields()).forEach(field -> {
            System.out.println(field);
            field.setAccessible(true);
            try {
                System.out.println(field.get(user));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

}
