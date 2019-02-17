package top.hiccup.ratel.im.pool;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import top.hiccup.ratel.im.websocket.jetty.JettyWebSocket;


/**
 * socket会话池
 *
 * @author wenhy
 * @date 2019/1/26
 */
public class ImSessionHolder {

    /**
     * 保存用户连接会话
     */
    private static final Map<Long, JettyWebSocket> userSessions = new ConcurrentHashMap<>(256);

    /**
     * 保存员工连接会话
     */
    private static final Map<Long, JettyWebSocket> staffSessions = new ConcurrentHashMap<>(16);

    /**
     * 用户连接上线
     */
    public static void addUserSession(Long userId, JettyWebSocket socket) {
        userSessions.put(userId, socket);
    }

    /**
     * 员工连接上线
     */
    public static void addStaffSession(Long staffId, JettyWebSocket socket) {
        staffSessions.put(staffId, socket);
    }

    /**
     * 获取所有在线的员工
     */
    public static Collection<JettyWebSocket> getAllStaffSession() {
        return staffSessions.values();
    }

    /**
     * 用户下线，移除会话
     */
    public static void removeUserSession(Long userId) {
        userSessions.remove(userId);
    }

    /**
     * 用户下线，移除会话
     */
    public static void removeStaffSession(Long staffId) {
        staffSessions.remove(staffId);
    }

    /**
     * 获取用户会话
     */
    public static JettyWebSocket getUserSession(Long userId) {
        return userSessions.get(userId);
    }

    /**
     * 获取员工会话
     */
    public static JettyWebSocket getStaffSession(Long staffId) {
        return staffSessions.get(staffId);
    }
}
