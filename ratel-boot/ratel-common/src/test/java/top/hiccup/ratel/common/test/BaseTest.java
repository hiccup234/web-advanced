package top.hiccup.ratel.common.test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;

import java.util.stream.Stream;

/**
 * testng 测试基类
 *
 * @author wenhy
 * @date 2021/6/7
 */
@ActiveProfiles("local")
@SpringBootTest(classes = AppConfig.class)
@TestExecutionListeners(MockitoTestExecutionListener.class)
public abstract class BaseTest extends AbstractTestNGSpringContextTests {

    @BeforeMethod
    public void beforeMethod() {
        Object[] args = Stream.of(this.getClass().getDeclaredFields())
                .filter(field -> field.getAnnotation(Mock.class) != null || field.getAnnotation(Spy.class) != null || field.getAnnotation(MockBean.class) != null || field.getAnnotation(SpyBean.class) != null)
//                .map(field -> ClassUtil.getPropertyValue(this, field.getName()))
                .toArray();
        Mockito.reset(args);
    }
}
