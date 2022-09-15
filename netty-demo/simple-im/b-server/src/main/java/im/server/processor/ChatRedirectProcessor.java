package im.server.processor;

import im.common.dto.msg.ProtoMsg;
import im.server.session.ServerSession;
import im.server.session.SessionMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChatRedirectProcessor implements ServerProcessor {

    @Override
    public ProtoMsg.HeadType type() {
        return ProtoMsg.HeadType.MESSAGE_REQUEST;
    }

    @Override
    public boolean action(ServerSession fromSession, ProtoMsg.Message proto) {
        // 聊天处理
        ProtoMsg.MessageRequest msg = proto.getMessageRequest();
        String to = msg.getTo(); // 接收方的 chatID
        log.info("chatMsg | from=" + msg.getFrom()
                + " , to=" + to
                + " , content=" + msg.getContent());
        List<ServerSession> toSessions = SessionMap.inst().getSessionsBy(to);
        if (toSessions == null) {
            // 接收方离线
            log.info("[" + to + "] 不在线，发送失败!");
        } else {
            toSessions.forEach((session) -> {
                // 将 IM 消息发送到接收方
                session.writeAndFlush(proto);
            });
        }
        return true;
    }

}
