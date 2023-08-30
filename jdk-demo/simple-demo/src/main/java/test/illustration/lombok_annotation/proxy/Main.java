package test.illustration.lombok_annotation.proxy;

import java.util.List;

public class Main {

    public static void main( String[] args ) {
        ITest test = TestProxy.of();
        test.test( "bb" );
        int value = test.get( "aa" );
        System.out.println( "get-value => " + value );
        List<String> find = test.find();
        System.out.println( "find-value => " + find );
    }

}
