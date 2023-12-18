package cn.simple.test.new_features.jdk9.jli;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.Arrays;

/**
 * 测试变量句柄。
 * <p>
 * 对象字段要 volatile 修饰。
 * <p>
 * Created by zengxf on 2017/12/27.
 */
public class TestVarHandle {
    public static void main(String[] arr) throws NoSuchFieldException, IllegalAccessException {
        testArray();
        System.out.println("-----------------------------");
        testObject();
    }

    static void testObject() throws NoSuchFieldException, IllegalAccessException {
        Point point = new Point();
        VarHandle vh = MethodHandles.lookup().findVarHandle(Point.class, "x", int.class);
        vh.setVolatile(point, 2);
        System.out.println(point);
        boolean sign = vh.compareAndSet(point, 2, 4); // ref, expected, new
        System.out.println("sign => " + sign);
        System.out.println(point);
        System.out.println("get => " + vh.get(point));
    }

    static class Point {
        volatile int x, y;

        @Override
        public String toString() {
            return "Point{" + "x=" + x + ", y=" + y + '}';
        }
    }

    static void testArray() {
        String[] sa = new String[15];
        Arrays.fill(sa, "0");
        System.out.println(Arrays.toString(sa));

        VarHandle avh = MethodHandles.arrayElementVarHandle(String[].class);
        boolean r = avh.compareAndSet(sa, 10, "0", "new"); // ref, index, expected, new
        System.out.println(r);

        System.out.println(Arrays.toString(sa));
    }
}
