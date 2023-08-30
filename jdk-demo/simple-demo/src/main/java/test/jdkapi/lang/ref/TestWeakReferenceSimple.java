package test.jdkapi.lang.ref;

import java.lang.ref.WeakReference;

/**
 * 参考: https://blog.csdn.net/zzg1229059735/article/details/82715741
 */
public class TestWeakReferenceSimple {

    public static void main(String[] args) {
//        String str = "abc";
        String str = new String("abc");
        WeakReference<String> ref = new WeakReference<>(str);
        testGc(ref);
        str = null;
        testGc(ref);
    }

    static void testGc(WeakReference<String> ref) {
        System.gc();
        System.out.println("--------- gc ------");
        if (ref.get() == null) {
            System.out.println("Ref 被回收");
        } else {
            System.out.println("Ref: " + ref.get());
        }
    }

}
