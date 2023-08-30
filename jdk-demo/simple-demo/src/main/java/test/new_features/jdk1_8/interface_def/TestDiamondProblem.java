package test.new_features.jdk1_8.interface_def;

public class TestDiamondProblem {

    static interface A {
	default void test() {
	    System.out.println( "Hello from A" );
	}
    }

    static interface B extends A {
    }

    static interface C extends A {
	// void hello(); // 加上抽象方法时，C 更具体，因此 D 需实现 C 的方法
    }

    static class D implements B, C {
    };

    public static void main( String[] args ) {
	new D().test();
    }

}
