package test.new_features.jdk1_8.interface_def;

public interface IPrint {

    default void print() {
	System.out.println( "fuck" );
    }

    default void say() {
	System.out.println( "say 2" );
    }

}
