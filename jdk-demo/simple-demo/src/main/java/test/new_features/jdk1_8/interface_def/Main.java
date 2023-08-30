package test.new_features.jdk1_8.interface_def;

public class Main {
    static class Test1 extends Print implements IEcho {

    }

    static class D implements IPrint {
	public void print() {
	    System.out.println( "Hello from D" );
	}
    }

    static class C extends D implements IPrint, IEcho {
    }

    public static void main( String[] args ) {
	// 继承规则：父类 > 接口类(子) > 接口类
	new User().say();

	// 子接口更具体
	new Test1().print();

	//  
	new C().print();
    }

}
