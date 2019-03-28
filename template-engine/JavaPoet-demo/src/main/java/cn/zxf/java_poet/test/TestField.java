package cn.zxf.java_poet.test;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;

public class TestField {

    public static void main( String[] args ) {
        FieldSpec android = FieldSpec.builder( String.class, "android" )
                .addModifiers( Modifier.PRIVATE, Modifier.FINAL )
                .initializer( "$S + $L", "Lollipop v.", 5.0d )
                .build();
        
        TypeSpec helloWorld = TypeSpec.classBuilder( "HelloWorld" )
                .addModifiers( Modifier.PUBLIC )
                .addField( android )
                .addField( String.class, "robot", Modifier.PRIVATE, Modifier.FINAL )
                .build();

        System.out.println( helloWorld );
    }

}
