package cn.zxf.java_poet.test;

import java.io.IOException;
import java.util.Collections;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public class TestImportStatic {

    public static void main( String[] args ) throws IOException {
        ClassName hoverboard = ClassName.get( "com.mattel", "Hoverboard" );
        ClassName list = ClassName.get( "java.util", "List" );
        ClassName arrayList = ClassName.get( "java.util", "ArrayList" );
        TypeName listOfHoverboards = ParameterizedTypeName.get( list, hoverboard );

        ClassName namedBoards = ClassName.get( "com.mattel", "Hoverboard", "Boards" );

        MethodSpec beyond = MethodSpec.methodBuilder( "beyond" )
                .returns( listOfHoverboards )
                .addStatement( "$T result = new $T<>()", listOfHoverboards, arrayList )
                .addStatement( "result.add($T.createNimbus(2000))", hoverboard )
                .addStatement( "result.add($T.createNimbus(\"2001\"))", hoverboard )
                .addStatement( "result.add($T.createNimbus($T.THUNDERBOLT))", hoverboard, namedBoards )
                .addStatement( "$T.sort(result)", Collections.class )
                .addStatement( "return result.isEmpty() ? $T.emptyList() : result", Collections.class )
                .build();

        TypeSpec hello = TypeSpec.classBuilder( "HelloWorld" )
                .addMethod( beyond )
                .build();

        JavaFile.builder( "com.example.helloworld", hello )
                .addStaticImport( hoverboard, "createNimbus" )
                .addStaticImport( namedBoards, "*" )
                .addStaticImport( Collections.class, "*" )
                .indent( "     " )
                .build()
                .writeTo( System.out );
    }

}
