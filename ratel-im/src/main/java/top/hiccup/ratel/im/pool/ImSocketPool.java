//package top.hiccup.ratel.im.pool;
//
//import java.io.IOException;
//import java.nio.CharBuffer;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
//import top.hiccup.ratel.im.websocket.UserMessageInbound;
//
///**
// * socket连接池
// *
// * @author wenhy
// * @date 2019/1/25
// */
//public class ImSocketPool {
//
//    /**
//     * 保存连接的Map容器
//     */
//    private static final Map<String, UserMessageInbound> connections = new ConcurrentHashMap<String, UserMessageInbound>();
//
//    /**
//     * 向连接池中添加连接
//     */
//    public static void addMessageInbound(UserMessageInbound inbound) {
//        connections.put(inbound.getUser(), inbound);
//    }
//
//    /**
//     * 获取所有的在线用户
//     */
//    public static Set<String> getOnlineUser() {
//        return connections.keySet();
//    }
//
//    /**
//     * 移除连接
//     */
//    public static void removeMessageInbound(UserMessageInbound inbound) {
//        connections.remove(inbound.getUser());
//    }
//
//    /**
//     * 向特定的用户发送数据
//     */
//    public static void sendMessageToUser(String user, String message) {
//        try {
//            UserMessageInbound inbound = connections.get(user);
//            if (inbound != null) {
//                inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 向所有的用户发送消息
//     */
//    public static void sendMessageToAllUser(String message) {
//        try {
//            Set<String> keySet = connections.keySet();
//            for (String key : keySet) {
//                UserMessageInbound inbound = connections.get(key);
//                if (inbound != null) {
//                    inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
