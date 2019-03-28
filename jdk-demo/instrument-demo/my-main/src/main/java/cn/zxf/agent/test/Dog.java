package cn.zxf.agent.test;

public class Dog {

    public String hello() {
        return "wow wow~";
        // return this.custom();
    }

    String custom() {
        System.out.println( "进入 custom() ..." );
        return "test custom ...";
    }

}
