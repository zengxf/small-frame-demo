package test.new_features.jdk1_8.interface_def;

public interface IUser {

    default void print() {
	System.out.println( "fuck" );
    }

    void setName( String name );

    default void say() {
	System.out.println( "say 23" );
    }

}
