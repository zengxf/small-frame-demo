package test.jdkapi.regexp;

public class RegexpBaseTest {

    public static void main( String[] args ) {
        System.out.println( "abcabcab".replaceAll( "(a)b(c)", "$1#$2" ) ); // $1,$2 对应的是 组1,组2
        // ----------------------
        System.out.println( "a b".matches( "a\\hb" ) ); // \h 水平方向空格
        System.out.println( "a\nb".matches( "a\\vb" ) ); // \v 垂直方向空格
        // ----------------------
        System.out.println( "在".matches( "\\p{L}" ) ); // \p{L} 匹配任意语言的非空字符
        System.out.println( "　".matches( "\\p{Z}" ) ); // \p{Z} 匹配任意语言的空字符或不可见字符
        // ----------------------
        System.out.println( ",,,,,123,45,67".replaceAll( "\\G(?!^),", "-" ) ); // \G 匹配第一个
        System.out.println( "abc abc ab".replaceAll( "\\G(a)b(c)", "$1#$2" ) );
        String Gstr = "{%abc%, %edc%} {%ab%, %dd%}".replaceAll( "(^[^{]*\\{|\\G,\\h*)%([^%]*)%", "$1#$2#" );
        System.out.println( Gstr );
        // ----------------------
        System.out.println( "abc abc abc".replaceAll( "(^abc)", "-" ) ); // ^ 在组里面也是匹配行的开头
        // ----------------------
        System.out.println( "foodie".matches( "^foo(d|die|lish)$" ) );
        System.out.println( "foodie".matches( "^foo(?:d|die|lish)$" ) );
        System.out.println( "foodie".matches( "^foo(?>d|die|lish)$" ) ); // 从短到长不会回溯，因此只匹配了 d，而没有 die 及后面的
        System.out.println( "foodie".matches( "^foo(?>lish|die|d)$" ) ); // 从长到短会回溯
        // ----------------------
        System.out.println( "(?<=): " + "b123".matches( "(?<!a)\\d+" ) );
        System.out.println( "(?!): " + "0001".matches( "^(?!0+$)\\d+$" ) );
        System.out.println( "(?<!\\d)\\.(?!\\d): " + "2.3".matches( "(?<!\\d)\\.(?!\\d)" ) );
        System.out.println( "(?<!\\d)\\.(?!\\d): " + "a.b".matches( "(?<!\\d)\\.(?!\\d)" ) );
        // ----------------------
        System.out.println( "abcdefgh".replaceAll( "(?=(.{3})+$)", ":" ) ); // 注意: $
    }

}
