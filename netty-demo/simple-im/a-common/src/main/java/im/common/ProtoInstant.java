package im.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/15.
 */
public class ProtoInstant {

    /*** 魔数，可以通过配置获取 */
    public static final short MAGIC_CODE = 0x86;
    /*** 版本号 */
    public static final short VERSION_CODE = 0x01;

    /*** 客户端平台     */
    public interface Platform {
        int WINDOWS = 1;
        int MAC = 2;
        int ANDROID = 3;
        int IOS = 4;
        int WEB = 5;
        int UNKNOWN = 6;
    }

    /*** 返回码枚举类 */
    @AllArgsConstructor
    @Getter
    public enum ResultCodeEnum {
        SUCCESS(0, "Success"),
        AUTH_FAILED(1, "登录失败"),
        NO_TOKEN(2, "没有授权码"),
        UNKNOWN_ERROR(3, "未知错误"),
        ;

        private Integer code;
        private String desc;
    }

}
