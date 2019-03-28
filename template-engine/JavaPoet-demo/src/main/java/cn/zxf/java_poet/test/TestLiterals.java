package cn.zxf.java_poet.test;

import com.squareup.javapoet.MethodSpec;

// $L 字面量
public class TestLiterals {

    public static void main( String[] args ) {
        MethodSpec main = MethodSpec.methodBuilder( "test" )
                .returns( int.class )
                .addStatement( "int result = 0" )
                .beginControlFlow( "for (int i = $L; i < $L; i++)", 0, 10 )
                .addStatement( "result = result $L i", "+" )
                .endControlFlow()
                .addStatement( "return result" )
                .build();

        System.out.println( main );
    }

}
