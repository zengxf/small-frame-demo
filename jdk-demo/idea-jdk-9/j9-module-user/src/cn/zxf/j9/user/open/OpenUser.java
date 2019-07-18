package cn.zxf.j9.user.open;

/**
 * Created by zengxf on 2017/10/11.
 */
public class OpenUser {

    private String name;
    private int age;

    private void testPri() {
        System.out.println("test pri");
    }

    public String getName() {
        return name;
    }

    public OpenUser setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public OpenUser setAge(int age) {
        this.age = age;
        return this;
    }

}
