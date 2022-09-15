package im.server.processor;

import im.common.ProtoInstant;
import im.common.dto.User;
import im.common.dto.msg.ProtoMsg;
import im.server.converter.LoginResponseConverter;
import im.server.session.ServerSession;
import im.server.session.SessionMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/15.
 */
@Service
@Slf4j
public class LoginProcessor implements ServerProcessor {

    @Autowired
    private LoginResponseConverter converter;

    @Override
    public ProtoMsg.HeadType type() {
        return ProtoMsg.HeadType.LOGIN_REQUEST;
    }

    @Override
    public boolean action(ServerSession session, ProtoMsg.Message proto) {
        // 取出token验证
        ProtoMsg.LoginRequest info = proto.getLoginRequest();
        long seqNo = proto.getSequence();

        User user = User.fromMsg(info);

        // 检查用户
        boolean isValidUser = checkUser(user);
        if (!isValidUser) {
            ProtoInstant.ResultCodeEnum resultCode = ProtoInstant.ResultCodeEnum.NO_TOKEN;
            // 构造登录失败的报文
            ProtoMsg.Message response = converter.build(resultCode, seqNo, "-1");
            // 发送登录失败的报文
            session.writeAndFlush(response);
            return false;
        }

        session.setUser(user);
        // 服务端session和传输channel绑定的核心代码
        session.reverseBind();
        // 登录成功
        ProtoInstant.ResultCodeEnum resultCode = ProtoInstant.ResultCodeEnum.SUCCESS;
        // 构造登录成功的报文
        ProtoMsg.Message response = converter.build(resultCode, seqNo, session.getSessionId());
        // 发送登录成功的报文
        session.writeAndFlush(response);
        return true;
    }

    private boolean checkUser(User user) {
        if (SessionMap.inst().hasLogin(user)) {
            return false;
        }
        // 校验用户, 比较耗时的操作, 需要 100 ms 以上的时间
        return true;
    }

}
