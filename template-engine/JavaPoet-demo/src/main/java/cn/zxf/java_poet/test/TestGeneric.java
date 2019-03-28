package cn.zxf.java_poet.test;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public class TestGeneric {

    public static void main( String[] args ) throws IOException {
        ClassName hoverboard = ClassName.get( "com.mattel", "Hoverboard" );
        ClassName list = ClassName.get( "java.util", "List" );
        ClassName arrayList = ClassName.get( "java.util", "ArrayList" );
        TypeName listOfHoverboards = ParameterizedTypeName.get( list, hoverboard );

        System.out.println( listOfHoverboards );
        System.out.println( "-------------" );

        MethodSpec beyond = MethodSpec.methodBuilder( "beyond" )
                .returns( listOfHoverboards )
                .addStatement( "$T result = new $T<>()", listOfHoverboards, arrayList )
                .addStatement( "result.add(new $T())", hoverboard )
                .addStatement( "result.add(new $T())", hoverboard )
                .addStatement( "return result" )
                .build();

        System.out.println( beyond );
        System.out.println( "-------------" );

        TypeSpec helloWorld = TypeSpec.classBuilder( "HelloWorld" )
                .addModifiers( Modifier.PUBLIC, Modifier.FINAL )
                .addMethod( beyond )
                .build();

        JavaFile javaFile = JavaFile.builder( "com.example.helloworld", helloWorld )
                .indent( "    " )
                .build();

        javaFile.writeTo( System.out );
    }

}
