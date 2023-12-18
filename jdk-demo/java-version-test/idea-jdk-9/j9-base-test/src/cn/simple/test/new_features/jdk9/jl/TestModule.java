package cn.simple.test.new_features.jdk9.jl;

import java.lang.module.ModuleDescriptor;

/**
 * 测试模块API
 * <p>
 * Created by zengxf on 2017/10/11.
 */
public class TestModule {

    public static void main(String[] arr) {
        Module module = int.class.getModule();
        System.out.println(module.getName());

        ModuleDescriptor desc = module.getDescriptor();

        System.out.println(desc.isOpen());
        desc.opens().forEach(System.out::println);
        System.out.println("---------------------");

        desc.exports().forEach(System.out::println);
        System.out.println("---------------------");

        desc.uses().forEach(System.out::println);
        System.out.println("---------------------");

        desc.requires().forEach(System.out::println);
        System.out.println("---------------------");

        desc.provides().forEach(System.out::println);
        System.out.println("---------------------");
    }

}
