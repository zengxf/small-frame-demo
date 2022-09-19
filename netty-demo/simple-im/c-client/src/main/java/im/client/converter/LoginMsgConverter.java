package im.client.converter;

import im.client.session.ClientSession;
import im.common.dto.User;
import im.common.dto.msg.ProtoMsg;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/19.
 */
public class LoginMsgConverter extends BaseConverter {

    private final User user;

    public LoginMsgConverter(User user, ClientSession session) {
        super(ProtoMsg.HeadType.LOGIN_REQUEST, session);
        this.user = user;
    }

    public ProtoMsg.Message build() {
        ProtoMsg.Message.Builder outerBuilder = super.getOuterBuilder(-1);
        ProtoMsg.LoginRequest.Builder lb = ProtoMsg.LoginRequest.newBuilder()
                .setDeviceId(user.getDevId())
                .setPlatform(user.getPlatform().ordinal())
                .setToken(user.getToken())
                .setUid(user.getUid());
        ProtoMsg.Message requestMsg = outerBuilder.setLoginRequest(lb).build();
        return requestMsg;
    }

    public static ProtoMsg.Message build(User user, ClientSession session) {
        LoginMsgConverter converter = new LoginMsgConverter(user, session);
        return converter.build();
    }

}


