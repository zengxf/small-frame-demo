package test.jdkapi.reflex.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
public @interface MyTargets {

    MyTarget[] value(); // 类型要相互对应上

    // String name(); // 不能有其他属性

}
