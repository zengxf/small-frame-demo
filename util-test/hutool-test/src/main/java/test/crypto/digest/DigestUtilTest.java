package test.crypto.digest;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * 摘要测试
 * <p/>
 * ZXF 创建于 2024/12/13
 */
public class DigestUtilTest {

    public static void main(String[] args) {
        String str = "123456";
        System.out.println(DigestUtil.md5Hex(str));
        System.out.println(DigestUtil.md5Hex16(str));
        System.out.println(DigestUtil.sha1Hex(str));
        System.out.println(DigestUtil.sha256Hex(str));
        System.out.println(DigestUtil.sha512Hex(str));
    }

}
