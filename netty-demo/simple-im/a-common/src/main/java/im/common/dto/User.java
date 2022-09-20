package im.common.dto;

import im.common.dto.msg.ProtoMsg;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/15.
 */
@Slf4j
@Data
public class User {

    private static final AtomicInteger NO = new AtomicInteger(1);

    String uid = String.valueOf(NO.getAndIncrement());
    String devId = UUID.randomUUID().toString();
    String token = UUID.randomUUID().toString();
    String nickName = "Fa88";
    String sessionId;
    PLAT_TYPE platform = PLAT_TYPE.WINDOWS;

    public enum PLAT_TYPE {
        WINDOWS, MAC, ANDROID, IOS, WEB, OTHER;
    }

    public static User fromMsg(ProtoMsg.LoginRequest info) {
        User user = new User();
        user.uid = info.getUid();
        user.devId = info.getDeviceId();
        user.token = info.getToken();
        user.setPlatform(info.getPlatform());
        log.info("登录中: {}", user);
        return user;
    }

    public void setPlatform(int platform) {
        PLAT_TYPE[] values = PLAT_TYPE.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].ordinal() == platform) {
                this.platform = values[i];
            }
        }
    }

}
