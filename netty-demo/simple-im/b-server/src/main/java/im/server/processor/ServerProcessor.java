package im.server.processor;

import im.common.dto.msg.ProtoMsg;
import im.server.session.ServerSession;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/15.
 */
public interface ServerProcessor {

    ProtoMsg.HeadType type();

    boolean action(ServerSession ch, ProtoMsg.Message proto);

}
