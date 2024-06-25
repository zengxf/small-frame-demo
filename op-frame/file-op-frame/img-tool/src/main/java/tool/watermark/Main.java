package tool.watermark;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * <p/>
 * Created by ZXFeng on 2024/6/25
 */
public class Main {

    public static void main(String[] args) throws Exception {
        // singleTest();    // 单个文件测试
        folderTest();    // 文件夹测试
    }

    static void folderTest() throws IOException {
        String srcFolder = "D:\\Data\\test\\img";
        String outFolder = srcFolder + "\\水印";
        String text = "测试-123，他用无效";

        Path outPath = Path.of(outFolder);
        boolean isExists = Files.exists(outPath);
        if (!isExists) {
            Files.createDirectories(outPath);
        }

        File srcF = new File(srcFolder);
        File[] childFiles = srcF.listFiles();
        if (childFiles == null) {
            return;
        }

        Stream.of(childFiles)
                .forEach(srcFile -> {
                    if (srcFile.isDirectory()) {
                        return;
                    }

                    ImgBO bo = new ImgBO();
                    bo.srcPath = srcFile.getAbsolutePath();
                    bo.text = text;
                    bo.outPath = outFolder + File.separator + srcFile.getName();

                    try {
                        ImgUtils.imageByText(bo);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }


    static void singleTest() throws Exception {
        ImgBO bo = new ImgBO();
        bo.srcPath = "D:\\Data\\test\\img\\xx31.png";
        bo.text = "测试-test";
        bo.outPath = "D:\\Data\\test\\img\\xx31-1.png";

        ImgUtils.imageByText(bo);
    }

}
