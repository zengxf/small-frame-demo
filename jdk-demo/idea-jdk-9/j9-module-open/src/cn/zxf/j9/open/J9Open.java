package cn.zxf.j9.open;

/**
 * Created by zengxf on 2017/10/11.
 */
public class J9Open {

    private String name;

    public void test() {
        System.out.println("open test ...");
    }

    private void testPri() {
        System.out.println("test pri");
    }

    public String getName() {
        return name;
    }

    public J9Open setName(String name) {
        this.name = name;
        return this;
    }
}
