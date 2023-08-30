package test.new_features.jdk1_8.lambda;

public class TestScope {

    int count;
    static int num;

    void test() {
        Runnable run = () -> {
            // lambda 内部对于实例的字段以及静态变量是即可读又可写
            count++;
            num++;
        };
        run.run();
    }

    void print() {
        System.out.println(String.format("%d - %d", count, num));
    }

    public static void main(String[] args) {
        TestScope scope = new TestScope();
        scope.test();
        scope.print();
    }

}
