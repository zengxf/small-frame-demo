package cn.zxf.test;

public class TestModule {

    public static void main( String[] args ) {
        Class<TestModule> cls = TestModule.class;
        Module mod = cls.getModule();
        String moduleName = mod.getName();
        System.out.printf( "Module Name: %s%n", moduleName );
    }

}
