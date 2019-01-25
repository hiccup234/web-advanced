package top.hiccup.ratel.im.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户信息
 *
 * @author wenhy
 * @date 2019/1/25
 */
@Data
@AllArgsConstructor
public class User {

    private long userId;

    private String name;

    private String account;

    private String password;
}
