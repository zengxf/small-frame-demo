package cn.zxf.java_poet.test;

import java.io.IOException;
import java.util.Date;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

// $T 类型，自动生成 import 语句
public class TestTypes {

    public static void main( String[] args ) throws IOException {
        ClassName hoverboard = ClassName.get( "com.mattel", "Hoverboard" );

        MethodSpec tomorrow = MethodSpec.methodBuilder( "tomorrow" )
                .addModifiers( Modifier.PUBLIC, Modifier.STATIC )
                .returns( hoverboard )
                .addStatement( "return new $T()", hoverboard )
                .build();

        MethodSpec today = MethodSpec.methodBuilder( "today" )
                .addModifiers( Modifier.PUBLIC, Modifier.STATIC )
                .returns( Date.class )
                .addStatement( "return new $T()", Date.class )
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder( "HelloWorld" )
                .addModifiers( Modifier.PUBLIC, Modifier.FINAL )
                .addMethod( today )
                .addMethod( tomorrow )
                .build();

        JavaFile javaFile = JavaFile.builder( "com.example.helloworld", helloWorld )
                .indent( "    " )
                .build();

        javaFile.writeTo( System.out );
    }

}
