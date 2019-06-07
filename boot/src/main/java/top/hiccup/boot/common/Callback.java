package top.hiccup.boot.common;

/**
 * 回调门面
 *
 * @author wenhy
 * @date 2019/6/5
 */
public interface Callback<T> {

    void onSuccess(T result);

    void onFailure(T result);
}
