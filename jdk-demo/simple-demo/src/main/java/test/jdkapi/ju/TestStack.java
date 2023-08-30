package test.jdkapi.ju;

import java.util.Stack;

public class TestStack {

    public static void main( String[] args ) {
        Stack<String> s = new Stack<>();
        s.add( "aa" );
        s.push( "dd" );
        s.push( "ee" ); // 
        System.out.println( s );
        
    }
    
}
