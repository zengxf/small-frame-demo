package cn.zxf.apt.demo;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class RouteProcessor extends AbstractProcessor {

    /** 文件相关的辅助类 用于生成新的源文件、class 等 */
    private Filer mFiler;

    // 主要做一些初始化操作
    @Override
    public synchronized void init( ProcessingEnvironment env ) {
        System.out.println( "==== 初始化 ====" );
        System.out.println( "==== 初始化 env ==== " + env );
        if ( env != null ) {
            System.out.println( "==== 初始化 env.filer ==== " + env.getFiler() );
        }

        super.init( env );
        mFiler = processingEnv.getFiler();
    }

    private boolean sign;

    // 具体处理注解的逻辑，控制代码的生成
    @Override
    public boolean process( Set<? extends TypeElement> annoations, RoundEnvironment env ) {
        System.out.println( "==== 处理 ====" );
        if ( !sign ) {
            System.out.println( "==== generateCode ====" );
            generateCode();
            sign = true;
        }
        System.out.println( "==== annoations ==== " + annoations );
        System.out.println( "==== 处理 env ==== " + env );
        return false;
    }

    // 使用 JavaPoet 生成代码
    void generateCode() {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder( "show" )
                .addModifiers( Modifier.PUBLIC )
                .addStatement( "String test = $S", "hello annotation world!" );

        /** 构建类 */
        TypeSpec routeClass = TypeSpec.classBuilder( "RouteGenerate" )
                .addModifiers( Modifier.PUBLIC )
                .addMethod( methodBuilder.build() )
                .build();

        try {
            JavaFile.builder( "cn.zxf.apt.demo", routeClass )
                    .indent( "    " )
                    .build()
                    .writeTo( mFiler );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    // 支持处理的注解类型, 如：@Route
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        System.out.println( "==== 注解类型 ====" );
        Set<String> set = new HashSet<>();
        set.add( Route.class.getName() );
        return set;
    }

    // java 版本，如：jdk1.6 or jdk1.7
    @Override
    public SourceVersion getSupportedSourceVersion() {
        System.out.println( "==== 支持版本 ====" );
        SourceVersion version = SourceVersion.latestSupported();
        System.out.println( "==== version ==== " + version );
        return version;
    }

}
