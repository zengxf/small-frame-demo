package test.jdkapi.io.file.privileged;

import java.io.File;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * <br>
 * Created by ZXFeng on 2023/9/18
 */
public class FileUtil {

    // 工程 A 执行文件的路径
    public final static String FOLDER_PATH = "D:\\Data\\test\\javatest";

    public static void makeFile(String fileName) {
        try {
            // 尝试在工程 A 执行文件的路径中创建一个新文件
            File fs = new File(FOLDER_PATH + "\\" + fileName);
            fs.createNewFile();
        } catch (Exception e) {
            System.out.println("file-name: " + fileName + " 创建出错！");
            e.printStackTrace(System.out);
        }
    }

    public static void doPrivilegedAction(final String fileName) {
        // 用特权访问方式创建文件
        AccessController.doPrivileged(new PrivilegedAction<String>() {
            @Override
            public String run() {
                makeFile(fileName);
                return null;
            }
        });
    }

}
