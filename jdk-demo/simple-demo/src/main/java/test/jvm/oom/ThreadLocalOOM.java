package test.jvm.oom;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 参考： https://blog.csdn.net/zzg1229059735/article/details/82715741
 */
public class ThreadLocalOOM {

    public ThreadLocal<List<Object>> local = new ThreadLocal<List<Object>>() {
        @Override
        protected List<Object> initialValue() {
            List<Object> list = new ArrayList<Object>();
            for (int i = 0; i < 10000; i++) {
                list.add(String.valueOf(i));
            }
            return list;
        }
    };

    public List<Object> get() {
        return local.get();
    }

    public void remove() {
        local.remove();
    }

    public static void main(String[] args) {
        //  为了复现key被回收的场景，我们使用临时变量
        ThreadLocalOOM memeory = new ThreadLocalOOM();

        // 调用
        incrementSameThreadId(memeory);

        System.out.println("GC前：key: " + memeory.local);
        System.out.println("GC前：value-size: " + refelectThreadLocals(Thread.currentThread()));

        // 设置为null，调用gc并不一定触发垃圾回收，但是可以通过java提供的一些工具进行手工触发gc回收。
        memeory.local = null;
        System.gc();

        System.out.println("GC后：key: " + memeory.local);
        System.out.println("GC后：value-size: " + refelectThreadLocals(Thread.currentThread()));

        // 模拟线程一直运行
        while (true) {
        }
    }

    private static void incrementSameThreadId(final ThreadLocalOOM memeory) {
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread() + "_" + i + ",threadId:" + memeory.get().size());
            }
        } finally {
            // 使用后请清除，这里为了复现内存泄漏，故意不回收
            // ThreadLocalMemory.remove();
        }
    }

    public static Object refelectThreadLocals(Thread t) {
        try {
            // Thread
            Field field = findField(Thread.class, "threadLocals");
            field.setAccessible(true);
            Object localmap = getField(field, t);

            // ThreadLocalMap.Entry[]
            Field entryField = findField(localmap.getClass(), "table");
            entryField.setAccessible(true);
            Object[] entry = (Object[]) getField(entryField, localmap);

            List<Object> list = new ArrayList<>();
            for (Object o : entry) {
                if (o != null)
                    list.add(o);
            }

            List<Object> result = new ArrayList<>();
            for (Object o : list) {
                // Entry.value
                Field entryValue = findField(o.getClass(), "value");
                entryValue.setAccessible(true);
                Object keyvalue = getField(entryValue, o);
                if (keyvalue instanceof ArrayList) {
                    result.add(keyvalue);
                }
            }
            return ((ArrayList<?>) result.get(0)).size();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    static Object getField(Field field, Object o) {
        try {
            return field.get(o);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static Field findField(Class clazz, String fName) {
        try {
            return clazz.getDeclaredField(fName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

}
