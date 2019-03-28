package cn.zxf.java_poet.test;

import com.squareup.javapoet.MethodSpec;

public class TestGenerateMethod {

    public static void main( String[] args ) {
        MethodSpec main = MethodSpec.methodBuilder( "main" )
                .addCode( "" //
                        + "int total = 0;\n" //
                        + "for (int i = 0; i < 10; i++) {\n" //
                        + "  total += i;\n" //
                        + "}\n" ) //
                .build();

        System.out.println( main.toString() );

        main = MethodSpec.methodBuilder( "main" )
                .addStatement( "int total = 0" )
                .beginControlFlow( "for (int i = 0; i < 10; i++)" )
                .addStatement( "total += i" )
                .endControlFlow()
                .build();

        System.out.println( main.toString() );
    }

}
