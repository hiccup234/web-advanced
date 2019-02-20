package top.hiccup.rpc.bean;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author wenhy
 * @date 2019/2/20
 */
public class User implements Serializable {

    private static final long serialVersionUID = 2665259552514378010L;

    private long id;

    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
