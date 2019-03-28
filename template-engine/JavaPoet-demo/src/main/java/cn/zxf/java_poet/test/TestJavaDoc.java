package cn.zxf.java_poet.test;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.MethodSpec;

public class TestJavaDoc {

    public static void main( String[] args ) {
        MethodSpec dismiss = MethodSpec.methodBuilder( "dismiss" )

                .addJavadoc( "Hides {@code message} from the caller's history. Other\n" //
                        + "participants in the conversation will continue to see the\n" //
                        + "message in their own history unless they also delete it.\n" )
                .addJavadoc( "\n" )
                .addJavadoc( "<p>Use {@link #delete($T)} to delete the entire\n" //
                        + "conversation for all participants.\n", Modifier.class )

                .addModifiers( Modifier.PUBLIC, Modifier.ABSTRACT )
                .addParameter( String.class, "message" )

                .build();

        System.out.println( dismiss );
    }

}
