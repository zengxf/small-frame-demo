package test.ocr;

/**
 * 测试识别中文
 * <p/>
 * 效果不行，识别不出中文
 * <p/>
 * ZXF 创建于 2025/2/22
 */
@Deprecated
public class OcrZhTest extends BaseTest {

    public static void main(String[] args) {
        String imgPath = "C:/Users/656553/Desktop/test/56.png";
        String language = "chi_sim+eng";
        start(imgPath, language);
    }

}
