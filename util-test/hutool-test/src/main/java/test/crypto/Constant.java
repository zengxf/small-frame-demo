package test.crypto;

import cn.hutool.crypto.digest.DigestUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 常量接口
 * <p/>
 * ZXF 创建于 2024/12/13
 */
public interface Constant {

    Charset UTF8 = StandardCharsets.UTF_8;
    String KEY_STR = "ZXF-6688 on 2024 # 私 key";
    String KEY_MD5 = DigestUtil.md5Hex16(KEY_STR);  // 128 bit
    byte[] KEY = KEY_MD5.getBytes(UTF8);

    String ENCRYPT_SUFFIX = ".en_data";

}