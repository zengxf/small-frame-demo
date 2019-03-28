package cn.zxf.java_poet.test;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class TestConstructor {

    public static strictfp void main( String[] args ) {
        MethodSpec flux = MethodSpec.constructorBuilder()
                .addModifiers( Modifier.PUBLIC )
                .addParameter( String.class, "greeting" )
                .addStatement( "this.$N = $N", "greeting", "greeting" )
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder( "HelloWorld" )
                .addModifiers( Modifier.PUBLIC )
                .addField( String.class, "greeting", Modifier.PRIVATE, Modifier.FINAL )
                .addMethod( flux )
                .build();
        
        System.out.println( helloWorld );
    }

}
