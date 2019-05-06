package top.hiccup.blueprint.service;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wenhy on 2018/1/20.
 */
public class SessionUtils {

    private static class InnerFactory {
        private static SqlSessionFactory sqlSessionFactory = null;
        static {
            try {
                InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static SqlSession getSession() {
        // 静态内部类实现单例模式
        return InnerFactory.sqlSessionFactory.openSession();
    }
}
