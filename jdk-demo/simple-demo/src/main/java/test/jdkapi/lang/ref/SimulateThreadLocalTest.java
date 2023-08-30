package test.jdkapi.lang.ref;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;

public class SimulateThreadLocalTest {

    public static void main(String[] args) {
        HashMap<Ref, Object> map = new HashMap<>();
        Ref key1 = new Ref(new String("a"));
        Ref key2 = new Ref(new String("b"));
        map.put(key1, 10);
        map.put(key2, 20);
        System.out.println("key1: " + key1.get());
        System.out.println("key2: " + key2.get());
        System.out.println("map: " + map);
        System.out.println("-------- gc ----------");
        System.gc();
        { // 清理 key，相当于 ThreadLocal 的 expungeStaleEntry() 方法
            HashSet<Ref> keys = new HashSet<>(map.keySet());
            keys.forEach(k -> {
                if (k.get() == null)
                    map.remove(k);
            });
        }
        System.out.println("key1: " + key1.get());
        System.out.println("key2: " + key2.get());
        System.out.println("map: " + map);
    }

    public static class Ref extends WeakReference<String> {
        public Ref(String referent) {
            super(referent);
        }
    }

}
