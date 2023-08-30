package test.string.fmt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 格式化 markdown 文件
 * <br/>
 * Created by ZXFeng on 2023/1/10.
 */
public class FileMarkdownFmt {

    static String filePath = "D:\\MyData\\note-backup\\未整理\\temp.md";

    public static void main(String[] args) throws IOException {
        fmtFile();
        // fmtTempTest();
    }

    private static void fmtFile() throws IOException {
        System.out.println("\n--------------------------");
        long st = System.currentTimeMillis();

        Path path = Paths.get(filePath);
        String str = Files.readString(path);
        str = fmt(str);
        Files.writeString(path, str);

        System.out.println("格式完成，用时（ms）：" + (System.currentTimeMillis() - st));
        System.out.println("--------------------------\n");
    }

    static void fmtTempTest() {
        String str = """
                - 而无状态的 Handler，作为Context的成员，
                - 关联在ChannelHandler
                Context中。
                - 一个unsafe（完成）test.
                - ChannelPipeline流水线，所示：abc dd.
                                
                1. 吃饭
                睡觉，玩
                游戏
                                
                2. 运动.
                3. 看书
                                
                - Java Code
                ```java
                System.out.println(str);
                                
                System.out.println(str);
                ```
                                
                #### 4 级标题
                1. 内容.
                2. 内容.
                """;

        System.out.println("---------------------");
        System.out.println(str);

        System.out.println("---------------------");
        str = fmt(str);
        System.out.println(str);
    }

    static String fmt(String str) {
        str = FileAutoNewline.fmtStr(str);
        str = FileDelLine.fmtStr(str);
        str = FileAppendSpace.fmtStr(str);
        return str;
    }

}
