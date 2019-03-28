package cn.zxf.j9.user;

/**
 * Created by zengxf on 2017/10/11.
 */
public class User {

    private String name;
    private int age;

    private void testPri() {
        System.out.println("test pri");
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public User setAge(int age) {
        this.age = age;
        return this;
    }
}
