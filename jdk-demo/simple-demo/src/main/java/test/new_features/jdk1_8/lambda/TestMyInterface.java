package test.new_features.jdk1_8.lambda;

/**
 * <br/>
 * Created by ZXFeng on 2023/3/15.
 */
public class TestMyInterface {

    public static void main(String[] args) {
        Fun fun = () -> {
            System.out.println("xxx0");
            System.out.println("xxx1");
        };
        fun.fun();
    }

    // @FunctionalInterface // 不声明也可用 Lambda
    interface Fun {
        void fun();
    }

}
