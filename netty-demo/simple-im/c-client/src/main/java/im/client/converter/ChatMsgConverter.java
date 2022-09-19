package im.client.converter;

import im.client.session.ClientSession;
import im.common.dto.ChatMsg;
import im.common.dto.User;
import im.common.dto.msg.ProtoMsg;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/19.
 */
public class ChatMsgConverter extends BaseConverter {

    private ChatMsg chatMsg;
    private User user;

    private ChatMsgConverter(ClientSession session) {
        super(ProtoMsg.HeadType.MESSAGE_REQUEST, session);
    }

    public ProtoMsg.Message build(ChatMsg chatMsg, User user) {
        this.chatMsg = chatMsg;
        this.user = user;
        ProtoMsg.Message.Builder outerBuilder = super.getOuterBuilder(-1);
        ProtoMsg.MessageRequest.Builder cb = ProtoMsg.MessageRequest.newBuilder();
        // 填充字段
        this.chatMsg.fillMsg(cb);
        ProtoMsg.Message requestMsg = outerBuilder.setMessageRequest(cb).build();
        return requestMsg;
    }

    public static ProtoMsg.Message build(ChatMsg chatMsg, User user, ClientSession session) {
        ChatMsgConverter chatMsgConverter = new ChatMsgConverter(session);
        return chatMsgConverter.build(chatMsg, user);
    }

}