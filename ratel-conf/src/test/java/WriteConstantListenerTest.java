import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.hiccup.ratel.conf.HiccupConst;

/**
 * 统一配置中心测试类
 *
 * @author wenhy
 * @date 2019/1/22
 */
public class WriteConstantListenerTest {
    //驱动名称
    private static String jdbcClassName = "com.mysql.jdbc.Driver";
    //数据库地址
    private static String dbUrl = "jdbc:mysql://47.106.155.243:3306/ratel-conf?useUnicode=true&characterEncoding=utf8";
    //用户名
    private static String dbUserName = "root";
    //密码
    private static String dbPassword = "234234";

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        String queryString = "select * from conf_const";
        List<Map<String, String>> dbConf = queryDb(queryString);
        // 修改前
        System.out.println("我之前叫:" + HiccupConst.MY_NAME);
        for (Map<String, String> map : dbConf) {
            String key = map.get("key");
            String value = map.get("value");
            Field field = HiccupConst.class.getField(key);
            field.setAccessible(true);
            int modBack = field.getModifiers();
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            if (Modifier.isFinal(field.getModifiers())) {
                modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            }
            field.set(null, value);
            System.out.println("现在叫:" + HiccupConst.MY_NAME);
        }
    }

    public static List<Map<String, String>> queryDb(final String queryString) {
        try {
            Class.forName(jdbcClassName);
            System.out.println("加载数据库驱动成功！");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("加载数据库驱动失败！");
        }
        Connection conn = null;
        List<Map<String, String>> result = new ArrayList<>(200);
        try {
            conn = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
            System.out.println("获取数据库连接成功！");
            PreparedStatement pst = conn.prepareStatement(queryString);
            ResultSet rSet = pst.executeQuery();
            while (rSet.next()) {
                String key = rSet.getString(2);
                String value = rSet.getString(3);
                Map<String, String> map = new HashMap<>();
                map.put("key", key);
                map.put("value", value);
                result.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("获取数据库连接失败！");
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
