package im.client.converter;

import im.client.session.ClientSession;
import im.common.dto.User;
import im.common.dto.msg.ProtoMsg;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/19.
 */
public class HeartBeatMsgConverter extends BaseConverter {

    private final User user;

    public HeartBeatMsgConverter(User user, ClientSession session) {
        super(ProtoMsg.HeadType.HEART_BEAT, session);
        this.user = user;
    }

    public ProtoMsg.Message build() {
        ProtoMsg.Message.Builder outerBuilder = super.getOuterBuilder(-1);
        ProtoMsg.MessageHeartBeat.Builder inner = ProtoMsg.MessageHeartBeat.newBuilder()
                .setSeq(0)
                .setJson("{\"from\": \"client\"}")
                .setUid(user.getUid());
        return outerBuilder.setHeartBeat(inner).build();
    }

}

