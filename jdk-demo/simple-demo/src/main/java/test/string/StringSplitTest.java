package test.string;

import java.util.Arrays;

public class StringSplitTest {
    public static void main( String[] args ) {
	String keyword = "华为  销售　  总监";
	String[] keywords = keyword.split( "\\s+|　+" );
	System.out.println( Arrays.toString( keywords ) );
    }
}
