package cn.zxf.j9.test.user;

import cn.zxf.j9.open.J9Open;
import cn.zxf.j9.user.User;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.Method;

/**
 * Created by zengxf on 2017/10/9.
 */
public class UserMain {

    public static void main(String[] arr) {
        System.out.println(int.class.getModule().getName());
        System.out.println(User.class.getModule().getName());

        try {
            Field field = User.class.getDeclaredField("name");
            field.setAccessible(true);
            System.out.println(field);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InaccessibleObjectException e) {
            System.out.println("field 模块没有打开");
            e.printStackTrace();
        }

        try {
            Method method = User.class.getDeclaredMethod("testPri");
            method.setAccessible(true);
            System.out.println(method);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InaccessibleObjectException e) {
            System.out.println("method 模块没有打开");
            e.printStackTrace();
        }

    }

}
