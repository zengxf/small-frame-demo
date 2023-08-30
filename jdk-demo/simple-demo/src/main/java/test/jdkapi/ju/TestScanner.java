package test.jdkapi.ju;

import java.util.Scanner;

/**
 * 令牌化-测试
 * <p>
 * Created by zengxf on 2019-12-06
 */
public class TestScanner {

    public static void main( String[] args ) {
        Scanner scanner = new Scanner( "abc; def; g;  hij;   1k" );
        scanner.useDelimiter( ";\\s*" );
        while ( scanner.hasNext() )
            System.out.println( scanner.next() );
        scanner.close();
    }

}
