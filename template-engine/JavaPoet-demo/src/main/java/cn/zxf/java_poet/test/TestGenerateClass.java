package cn.zxf.java_poet.test;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class TestGenerateClass {

    public static void main( String[] args ) throws IOException {
        MethodSpec main = MethodSpec.methodBuilder( "main" )
                .addModifiers( Modifier.PUBLIC, Modifier.STATIC )
                .returns( void.class )
                .addParameter( String[].class, "args" )
                .addStatement( "$T.out.println($S)", System.class, "Hello, JavaPoet!" )
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder( "HelloWorld" )
                .addModifiers( Modifier.PUBLIC, Modifier.FINAL )
                .addMethod( main )
                .build();

        JavaFile javaFile = JavaFile.builder( "com.example.helloworld", helloWorld )
                .indent( "    " )
                .build();

        javaFile.writeTo( System.out );
    }

}
