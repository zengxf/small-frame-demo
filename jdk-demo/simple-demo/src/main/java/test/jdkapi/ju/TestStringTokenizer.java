package test.jdkapi.ju;

import java.util.StringTokenizer;

/**
 * 令牌化-测试
 * <p/>
 * 参考：https://www.cnblogs.com/gaopeng527/p/4899237.html
 */
public class TestStringTokenizer {

    public static void main( String[] args ) {
        StringTokenizer st = new StringTokenizer( "ab;cd;ef;g", ";" );
        while ( st.hasMoreTokens() )
            System.out.println( st.nextToken() );

        System.out.println( "===============" );
        st = new StringTokenizer( "aba;cda;efa;ga" );
        while ( st.hasMoreTokens() )
            System.out.println( st.nextToken( "a;" ) ); // 或者关系
    }

}
