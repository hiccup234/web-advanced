package top.hiccup.ratel.conf;

import top.hiccup.ratel.conf.annotation.Const;

/**
 * Created by wenhy on 2018/1/28.
 */
@Const
public class HiccupConst {

    // 直接写成字面量常量在编译时会直接替换，导致后续的配置修改无效
    // public static final String MY_ = "wenhy";

    public static final String MY_NAME = new String("小海海");

    public static final Integer MY_AGE = new Integer(25);

    public static final String test = new String("22222");

}
