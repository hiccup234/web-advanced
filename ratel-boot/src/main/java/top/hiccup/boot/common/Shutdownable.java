package top.hiccup.boot.common;

/**
 * 可关闭的资源
 *
 * @author wenhy
 * @date 2019/6/5
 */
@FunctionalInterface
public interface Shutdownable {

    /**
     * 关闭资源
     * @param callback
     */
    void shutdown(Callback<Void> callback);
}
