package cn.zxf.java_poet.test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

public class TestAnonymousInnerClass {

    public static void main( String[] args ) {
        TypeSpec comparator = TypeSpec.anonymousClassBuilder( "" )
                .addSuperinterface( ParameterizedTypeName.get( Comparator.class, String.class ) )
                .addMethod( MethodSpec.methodBuilder( "compare" )
                        .addAnnotation( Override.class )
                        .addModifiers( Modifier.PUBLIC )
                        .addParameter( String.class, "a" )
                        .addParameter( String.class, "b" )
                        .returns( int.class )
                        .addStatement( "return $N.length() - $N.length()", "a", "b" )
                        .build() )
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder( "HelloWorld" )
                .addMethod( MethodSpec.methodBuilder( "sortByLength" )
                        .addParameter( ParameterizedTypeName.get( List.class, String.class ), "strings" )
                        .addStatement( "$T.sort($N, $L)", Collections.class, "strings", comparator )
                        .build() )
                .build();

        System.out.println( comparator );
        System.out.println( "----------------" );
        System.out.println( helloWorld );
    }

}
