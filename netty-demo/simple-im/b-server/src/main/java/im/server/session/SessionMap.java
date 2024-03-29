package im.server.session;

import im.common.dto.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/15.
 */
@Slf4j
public class SessionMap {

    private static SessionMap singleInstance = new SessionMap();

    private ConcurrentHashMap<String, ServerSession> map = new ConcurrentHashMap<>();   //会话集合


    public static SessionMap inst() {
        return singleInstance;
    }

    /*** 增加 session 对象 */
    public void addSession(ServerSession s) {
        map.put(s.getSessionId(), s);
        log.info("用户登录: id = " + s.getUser().getUid() + ", 在线总数: " + map.size());
    }

    /*** 根据用户id，获取session对象 */
    public List<ServerSession> getSessionsBy(String userId) {
        List<ServerSession> list = map.values()
                .stream()
                .filter(session -> session.getUser().getUid().equals(userId))
                .collect(Collectors.toList());
        return list;
    }

    /*** 删除session */
    public void removeSession(String sessionId) {
        if (!map.containsKey(sessionId)) {
            return;
        }
        ServerSession s = map.get(sessionId);
        map.remove(sessionId);
        log.info("用户下线:id= " + s.getUser().getUid() + "   在线总数: " + map.size());
    }

    public boolean hasLogin(User user) {
        Iterator<Map.Entry<String, ServerSession>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, ServerSession> next = it.next();
            User u = next.getValue().getUser();
            if (u.getUid().equals(user.getUid()) && u.getPlatform().equals(user.getPlatform())) {
                return true;
            }
        }
        return false;
    }

}
