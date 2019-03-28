package cn.zxf.java_poet.test;

import java.util.LinkedHashMap;
import java.util.Map;

import com.squareup.javapoet.CodeBlock;

public class TestCodeBlockFormat {

    public static void main( String[] args ) {
        System.out.println( CodeBlock.builder()
                .add( "I ate $L $L", 3, "tacos" )
                .build() );
        
        System.out.println( CodeBlock.builder()
                .add( "I ate $2L $1L", "tacos", 3 )
                .build() );

        Map<String, Object> map = new LinkedHashMap<>();
        map.put( "food", "tacos" );
        map.put( "count", 3 );
        System.out.println( CodeBlock.builder()
                .addNamed( "I ate $count:L $food:L", map )
                .build() );
    }

}
