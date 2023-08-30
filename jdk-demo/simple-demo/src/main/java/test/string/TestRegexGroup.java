package test.string;

public class TestRegexGroup {

    public static void main( String[] args ) {
        // 非捕获
        System.out.println( "aba sfed 为人 中国  s 要lkk".matches( "((?!.*?aa)(?=.*?为人).*)" ) );// 不包含
        System.out.println( "d AA sfed \r\n为人 中国  s 要lkk".matches( "(?is)((?=.*?aa)(?=.*?为人).*)" ) );// 加模式"/is"
        System.out.println( "aa sfed 为人 中国  s 要lkk".matches( "((?=.*?aa)(?=.*?为人).*)|((?=.*?对象)((?=.*?改变)|(?=.*?State)).*)" ) );
        System.out.println( "cc 改变 为人 中国 对象s 要lkk".matches( "((?=.*?aa)(?=.*?为人).*)|((?=.*?对象)((?=.*?改变)|(?=.*?State)).*)" ) );
        System.out.println( "cc 改State 为人 中国 对象s 要lkk".matches( "((?=.*?aa)(?=.*?为人).*)|((?=.*?对象)((?=.*?改变)|(?=.*?State)).*)" ) );
        // \b\w*q(?!u)\w*\b # q后面不是u的单词

        // 后向引用
        System.out.println( "aa bb aa".matches( "(aa).*?\\1" ) );

        // 起始与结束，无/regex/
        String regex = "(?m)^abc$";// 用于多行时
        String osName = "";
        osName.matches( "^(?i)Windows.*$" );

        // name组
        System.out.println( "zxf--zxf".matches( "(?<name>zxf)--(\\k<name>)" ) );

        // 注释
        regex = "(?xm)^aaa#注释) aa$(?=aa)";// '#'之后所有的表达式都当作注释
        System.out.println( regex );
        // 注意：m.find(); // 会跳到下一个匹配
    }

}
