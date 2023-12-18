package cn.zxf.j9.user.open2;

/**
 * Created by zengxf on 2017/10/11.
 */
public class OpenUser2 {

    private String name;
    private int age;

    private void testPri() {
        System.out.println("test pri");
    }

    public String getName() {
        return name;
    }

    public OpenUser2 setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public OpenUser2 setAge(int age) {
        this.age = age;
        return this;
    }

}
