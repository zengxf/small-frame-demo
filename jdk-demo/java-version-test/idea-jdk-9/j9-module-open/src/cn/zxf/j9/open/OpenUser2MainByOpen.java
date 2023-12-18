package cn.zxf.j9.open;

import cn.zxf.j9.user.open2.OpenUser2;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.Method;

/**
 * Created by zengxf on 2017/10/9.
 */
public class OpenUser2MainByOpen {

    public static void main(String[] arr) {
        System.out.println(OpenUser2.class.getModule().getName());

        try {
            Field field = OpenUser2.class.getDeclaredField("name");
            field.setAccessible(true);
            System.out.println(field);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InaccessibleObjectException e) {
            System.out.println("field 模块没有打开");
            e.printStackTrace();
        }

        try {
            Method method = OpenUser2.class.getDeclaredMethod("testPri");
            method.setAccessible(false);
            System.out.println(method);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InaccessibleObjectException e) {
            System.out.println("method 模块没有打开");
            e.printStackTrace();
        }

    }

}
