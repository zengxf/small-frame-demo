package cn.zxf.java_poet.test;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

// $S 
public class TestStrings {

    public static void main( String[] args ) throws Exception {
        TypeSpec helloWorld = TypeSpec.classBuilder( "HelloWorld" )
                .addModifiers( Modifier.PUBLIC, Modifier.FINAL )
                .addMethod( whatsMyName( "test" ) )
                .build();

        JavaFile javaFile = JavaFile.builder( "com.example.helloworld", helloWorld )
                .indent( "    " )
                .build();

        javaFile.writeTo( System.out );
    }

    private static MethodSpec whatsMyName( String name ) {
        return MethodSpec.methodBuilder( name )
                .returns( String.class )
                .addStatement( "return \"$S\"", name )
                .addStatement( "return $S", name )
                .build();
    }

}
