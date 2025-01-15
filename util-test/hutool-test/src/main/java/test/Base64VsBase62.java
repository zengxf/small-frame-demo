package test;

import cn.hutool.core.codec.Base62;
import cn.hutool.core.codec.Base62Codec;
import cn.hutool.core.codec.Base64;

/**
 * 测试结果：
 * <pre>
 * Base64 用时少
 * Base62 用时多 (差不多比 b64 多 100 倍)，
 *   原因是 {@link Base62Codec#convert(byte[], int, int)} 多次轮循
 * </pre>
 * <p/>
 * ZXF 创建于 2025/1/15
 */
public class Base64VsBase62 {

    public static void main(String[] args) {
        String str = "test -----a------b-测试---c------d------e-----f---- 123456 -----g-----h----i--j k l--- 中文 --";
        int num = 10000;

        String b62 = Base62.encode(str);
        String b64 = Base64.encode(str);

        System.out.println("b62: " + b62);
        System.out.println("b64: " + b64);


        // 编码用时测试

        long start = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            Base62.encode(str);
        }
        long b62Used = System.currentTimeMillis() - start;
        System.out.println("b62Used: " + b62Used);

        start = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            Base64.encode(str);
        }
        long b64Used = System.currentTimeMillis() - start;
        System.out.println("b64Used: " + b64Used);


        // 解码用时测试

        start = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            Base62.decode(b62);
        }
        long b62DUsed = System.currentTimeMillis() - start;
        System.out.println("b62DUsed: " + b62DUsed);

        start = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            Base64.decode(b64);
        }
        long b64DUsed = System.currentTimeMillis() - start;
        System.out.println("b64DUsed: " + b64DUsed);

        System.out.println("b62D: " + Base62.decodeStr(b62));
        System.out.println("b64D: " + Base64.decodeStr(b64));
    }

}
