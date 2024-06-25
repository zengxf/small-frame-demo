package tool.watermark;

import java.io.IOException;

/**
 * <p/>
 * Created by ZXFeng on 2024/6/25
 */
public class Main {

    public static void main(String[] args) throws IOException {
        ImgBO bo = new ImgBO();
        bo.srcPath = "D:\\Data\\test\\img\\xx31.png";
        bo.text = "测试-test";
        bo.outPath = "D:\\Data\\test\\img\\xx31-1.png";
        ImgUtils.handle(bo);
    }

}
