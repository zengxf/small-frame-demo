package cn.simple.test.new_features.jdk9.base;

/**
 * Created by zengxf on 2017/10/10.
 */
public interface IUser {

    default boolean isAtOddPos(char c) {
        return getPos(c) % 2 == 1;
    }

    default boolean isAtEvenPos(char c) {
        return getPos(c) % 2 == 0;
    }

    private int getPos(char c) {
        test();
        if (!Character.isLetter(c)) {
            throw new RuntimeException("Not a letter: " + c);
        }
        char uc = Character.toUpperCase(c);
        int pos = uc - 64;
        return pos;
    }

    private static void test(){
        System.out.println("-----");
    }

}
