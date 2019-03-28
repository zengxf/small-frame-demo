package cn.zxf.test_user;

/**
 * Created by zengxf on 2018/9/10.
 */
public class User {

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

}
