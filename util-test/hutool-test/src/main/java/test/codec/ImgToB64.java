package test.codec;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Console;

import java.io.File;

/**
 * 图片转 Base64
 * <p/>
 * 在线查看: https://tool.chinaz.com/tools/imgtobase
 * <p/>
 * ZXF 创建于 2025/2/22
 */
public class ImgToB64 {

    public static void main(String[] args) {
        // String imgPath = "C:/Users/656553/Desktop/test/02.png";
        String imgPath = "D:/line.png";

        String en = Base64.encode(new File(imgPath));

        // Console.log("en ({}): \n\ndata:image/jpeg;base64,{}\n\n", en.length(), en);      // jpg
        Console.log("en ({}): \n\ndata:image/png;base64,{}\n\n", en.length(), en);  // png
    }

}
