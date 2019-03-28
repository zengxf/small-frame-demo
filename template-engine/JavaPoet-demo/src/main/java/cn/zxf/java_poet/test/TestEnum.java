package cn.zxf.java_poet.test;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class TestEnum {

    public static void main( String[] args ) {
        TypeSpec helloWorld = TypeSpec.enumBuilder( "Roshambo" )
                .addModifiers( Modifier.PUBLIC )
                .addEnumConstant( "ROCK" )
                .addEnumConstant( "SCISSORS" )
                .addEnumConstant( "PAPER" )
                .build();

        System.out.println( helloWorld );
        System.out.println( "========================" );

        helloWorld = TypeSpec.enumBuilder( "Roshambo" )
                .addModifiers( Modifier.PUBLIC )

                .addEnumConstant( "ROCK", TypeSpec.anonymousClassBuilder( "$S", "fist" )
                        .addMethod( MethodSpec.methodBuilder( "toString" )
                                .addAnnotation( Override.class )
                                .addModifiers( Modifier.PUBLIC )
                                .addStatement( "return $S", "avalanche!" )
                                .returns( String.class )
                                .build() )
                        .build() )
                .addEnumConstant( "SCISSORS", TypeSpec.anonymousClassBuilder( "$S", "peace" )
                        .build() )
                .addEnumConstant( "PAPER", TypeSpec.anonymousClassBuilder( "$S", "flat" )
                        .build() )

                .addField( String.class, "handsign", Modifier.PRIVATE, Modifier.FINAL )

                .addMethod( MethodSpec.constructorBuilder()
                        .addParameter( String.class, "handsign" )
                        .addStatement( "this.$N = $N", "handsign", "handsign" )
                        .build() )

                .build();

        System.out.println( helloWorld.toString() );
    }

}
