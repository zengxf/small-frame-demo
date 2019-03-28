package cn.zxf.java_poet.test;

import java.lang.annotation.Target;
import java.util.logging.LogRecord;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;

public class TestAnnotation {

    public static void main( String[] args ) {
        MethodSpec toString = MethodSpec.methodBuilder( "toString" )
                .addAnnotation( Override.class )
                .returns( String.class )
                .addModifiers( Modifier.PUBLIC )
                .addStatement( "return $S", "Hoverboard" )
                .build();
        System.out.println( toString );
        System.out.println( "----------------------" );

        MethodSpec logRecord = MethodSpec.methodBuilder( "recordEvent" )
                .addModifiers( Modifier.PUBLIC, Modifier.ABSTRACT )
                .addAnnotation( AnnotationSpec.builder( Override.class )
                        .addMember( "accept", "$S", "application/json; charset=utf-8" )
                        .addMember( "userAgent", "$S", "Square Cash" )
                        .build() )
                .addParameter( LogRecord.class, "logRecord" )
                .returns( LogRecord.class )
                .build();

        System.out.println( logRecord );
        System.out.println( "----------------------" );

        logRecord = MethodSpec.methodBuilder( "recordEvent" )
                .addModifiers( Modifier.PUBLIC, Modifier.ABSTRACT )
                .addAnnotation( AnnotationSpec.builder( Override.class )
                        .addMember( "value", "$L", AnnotationSpec.builder( Target.class )
                                .addMember( "name", "$S", "Accept" )
                                .addMember( "value", "$S", "application/json; charset=utf-8" )
                                .build() )
                        .addMember( "value", "$L", AnnotationSpec.builder( Target.class )
                                .addMember( "name", "$S", "User-Agent" )
                                .addMember( "value", "$S", "Square Cash" )
                                .build() )
                        .build() )
                .addParameter( LogRecord.class, "logRecord" )
                .returns( LogRecord.class )
                .build();

        System.out.println( logRecord );
        System.out.println( "----------------------" );
    }

}
