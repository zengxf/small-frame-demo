package cn.zxf.java_poet.test;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class TestInterface {

    public static void main( String[] args ) {
        TypeSpec helloWorld = TypeSpec.interfaceBuilder( "HelloWorld" )
                .addModifiers( Modifier.PUBLIC )
                .addField( FieldSpec.builder( String.class, "ONLY_THING_THAT_IS_CONSTANT" )
                        .addModifiers( Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL )
                        .initializer( "$S", "change" )
                        .build() )
                .addMethod( MethodSpec.methodBuilder( "beep" )
                        .addModifiers( Modifier.PUBLIC, Modifier.ABSTRACT )
                        .build() )
                .build();

        System.out.println( helloWorld );
    }

}
