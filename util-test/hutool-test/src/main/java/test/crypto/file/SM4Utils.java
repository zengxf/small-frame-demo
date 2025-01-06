package test.crypto.file;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.crypto.digest.DigestUtil;
import test.crypto.Constant;

/**
 * <p/>
 * ZXF 创建于 2024/12/16
 */
public class SM4Utils implements Constant {

    static byte[] extractKeys(String[] args) {
        byte[] myKey = KEY; // 没有口令用默认的，否则用口令
        if (ArrayUtil.isNotEmpty(args)) {
            String keyStr = args[0];

            // 脱敏输出
            System.out.println(DesensitizedUtil.idCardNum(keyStr, 2, 2));

            String md5Hex16 = DigestUtil.md5Hex16(keyStr);  // 128 bit
            myKey = md5Hex16.getBytes(UTF8);
        }
        return myKey;
    }

}
