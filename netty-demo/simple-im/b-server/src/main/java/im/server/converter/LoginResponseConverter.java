package im.server.converter;

import im.common.ProtoInstant;
import im.common.dto.msg.ProtoMsg;
import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/15.
 */
@Service
public class LoginResponseConverter {

    public ProtoMsg.Message build(ProtoInstant.ResultCodeEnum en, long seqId, String sessionId) {
        ProtoMsg.Message.Builder outer = ProtoMsg.Message.newBuilder()
                .setType(ProtoMsg.HeadType.LOGIN_RESPONSE)  // 设置消息类型
                .setSequence(seqId)
                .setSessionId(sessionId);  // 设置应答流水，与请求对应

        ProtoMsg.LoginResponse.Builder b = ProtoMsg.LoginResponse.newBuilder()
                .setCode(en.getCode())
                .setInfo(en.getDesc())
                .setExpose(1);

        outer.setLoginResponse(b.build());
        return outer.build();
    }

}
