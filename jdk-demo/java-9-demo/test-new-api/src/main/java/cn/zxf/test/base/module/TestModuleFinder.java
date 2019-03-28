package cn.zxf.test.base.module;

import java.io.IOException;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.nio.ByteBuffer;
import java.util.Optional;

/**
 * Created by zengxf on 2018/9/11.
 */
public class TestModuleFinder {

    public static void main(String[] arr) {
        ModuleFinder finder = ModuleFinder.ofSystem();
        Optional<ModuleReference> omr = finder.find("java.base");
        ModuleReference ref = omr.get();
        System.out.println(ref.location().orElse(null));
        System.out.println(ref.descriptor().name());

        try (ModuleReader reader = ref.open()) {

            Optional<ByteBuffer> bb = reader.read("java/lang/Object.class");
            bb.ifPresent(buffer -> {
                System.out.println("Object.class Size: " + buffer.limit());
                reader.release(buffer);
            });

            System.out.println("\nFive resources in the java.base module:");
            reader.list()
                    .limit(5)
                    .forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
