package top.hiccup.blueprint.entity.po;

import java.io.Serializable;

/**
 * 用户实体类
 *
 * @author wenhy
 * @date 2018/7/27
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private String name;
    private Integer age;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
