package test.new_features.jdk1_8.interface_def;

public interface IEcho extends IPrint {
    default void print() {
	System.out.println( "fuck from IEcho" );
    }
}
