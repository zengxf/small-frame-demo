package test.new_features.jdk1_6.apt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于自动生成log成员变量.仅适用于class或enum,不适用于接口.
 */
@Retention( RetentionPolicy.SOURCE )
@Target( ElementType.TYPE )
public @interface MySlf4j {
    /**
     * 系统名称.如果为空则取"-Dvlogging.system"系统属性,如果系统属性也为空,则取"Unknown".
     */
    String system() default "";

    /**
     * 模块名称.如果为空则取"-Dvlogging.module"系统属性,如果系统属性也为空,则取"Unknown".
     */
    String module() default "";
}