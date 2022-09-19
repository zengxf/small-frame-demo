package im.client.converter;

import im.client.session.ClientSession;
import im.common.dto.msg.ProtoMsg;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/19.
 */
public class BaseConverter {

    protected ProtoMsg.HeadType type;
    private long seqId;
    private ClientSession session;

    public BaseConverter(ProtoMsg.HeadType type, ClientSession session) {
        this.type = type;
        this.session = session;
    }

    /*** 构建消息 基础部分 */
    public ProtoMsg.Message buildOuter(long seqId) {
        return getOuterBuilder(seqId).buildPartial();
    }

    public ProtoMsg.Message.Builder getOuterBuilder(long seqId) {
        this.seqId = seqId;
        ProtoMsg.Message.Builder mb = ProtoMsg.Message.newBuilder()
                .setType(type)
                .setSessionId(session.getSessionId())
                .setSequence(seqId);
        return mb;
    }

    /*** 构建消息 基础部分 的 Builder */
    public ProtoMsg.Message.Builder baseBuilder(long seqId) {
        this.seqId = seqId;
        ProtoMsg.Message.Builder mb = ProtoMsg.Message.newBuilder()
                .setType(type)
                .setSessionId(session.getSessionId())
                .setSequence(seqId);
        return mb;
    }

}
