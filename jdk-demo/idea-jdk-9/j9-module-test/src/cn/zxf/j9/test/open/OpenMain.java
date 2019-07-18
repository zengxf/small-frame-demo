package cn.zxf.j9.test.open;

import cn.zxf.j9.open.J9Open;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.Method;

/**
 * Created by zengxf on 2017/10/11.
 */
public class OpenMain {

    public static void main(String[] arr) {
        try {
            Field field = J9Open.class.getDeclaredField("name");
            field.setAccessible(true);
            System.out.println(field);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InaccessibleObjectException e) {
            System.out.println("模块没有打开");
            e.printStackTrace();
        }

        try {
            Method method = J9Open.class.getDeclaredMethod("test");
            method.setAccessible(true);
            method = J9Open.class.getDeclaredMethod("testPri");
            method.setAccessible(true);
            System.out.println(method);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InaccessibleObjectException e) {
            System.out.println("模块没有打开");
            e.printStackTrace();
        }
    }

}
