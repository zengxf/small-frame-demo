package test.privileged;

import java.io.File;

/**
 * <pre>
 * -Djava.security.policy=D:/Data/test/javatest/MyPolicy.txt.cfg
 * # -Djava.security.manager # 此参数可以不用加
 *
 * > 使用 cmd 命令运行：
 *   cd build/classes/java/main
 *   java "-Djava.security.policy=D:/Data/test/javatest/MyPolicy.txt.cfg" test.privileged.DemoDoPrivilege
 * </pre>
 * <p/>
 * ref: https://blog.csdn.net/u014470581/article/details/79262632 <br/>
 * ref: https://www.jianshu.com/p/81985bc2bfa3 <br/>
 * <p/>
 * Created by ZXFeng on 2023/9/18
 */
public class DemoDoPrivilege {

    public static void main(String[] args) {
        System.out.println("***************************************");

        // 打开系统安全权限检查开关
        System.setSecurityManager(new SecurityManager());

        System.out.println("-----------------------------------------");
        System.out.println("创建 temp1.txt ...");
        FileUtil.doPrivilegedAction("temp1.txt");

        System.out.println("-----------------------------------------");
        System.out.println("创建 temp2.txt ...");
        try {
            // 用普通文件操作方式在工程 A 执行文件路径中创建 temp2.txt 文件
            File fs = new File(FileUtil.FOLDER_PATH + "//temp2.txt");
            fs.createNewFile();
        } catch (Exception e) {
            System.out.println("创建 temp2.txt 出错！");
            e.printStackTrace(System.out);
        }

        System.out.println("-----------------------------------------");
        System.out.println("创建 temp3.txt ...");
        FileUtil.makeFile("temp3.txt");

        System.out.println("***************************************");
    }

}
