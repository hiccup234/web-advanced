package top.hiccup.blueprint.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wenhy on 2018/1/24.
 */

//@RunWith(SpringJunit4ClassRunner.class)
//ContextConfiguration(locations="classpath:spring/applicationContext.xml")
public class SpringTest {

    /**
     * 问：Spring是什么？
     * 1）Spring是一个容器
     * 2）用于降低代码间的耦合度
     * 3）采用依赖注入（IOC）和面向切面编程（AOP）两种技术来解耦
     */

    /**
     * 依赖注入(DI)、IOC：相对工厂模式就像外卖下单就送到门口，而工厂模式还要持有工厂对象引用
     *
     * 面向切面编程(AOP)：类似书签的作用，记录看到多少页或者标记精彩的故事情节，但是不影响故事情节
     *                    或者类似笔记本内存条插槽
     *                    可以从程序执行的动态角度来看
     */

    /**
     * ApplicationContext 与BeanFactory 容器的区别：Bean的创建时机不同
     * 1）ApplicationContext 容器在初始化时会创建所有配置的Bean
     *      优点：响应速度快（一般用ApplicationContext）
     *      缺点：占用系统资源（内存，CPU等），应用启动时耗时长
     * 2）BeanFactory 容器在初始化时并不创建Bean，而是在真正获取对象时才创建
     *      优点：使用时才创建，灵活控制，占用资源少
     *      缺点：响应速度相对较慢
     */

    public static void main(String[] args) {
        // 使用ApplicationContext容器
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
//        Animal animal = ctx.getBean("animal", Animal.class);
//        System.out.println(animal);

        // 使用FileSystemXmlApplicationContext容器
//        ctx = new FileSystemXmlApplicationContext("E:/MyWork/IntelliJ IDEA/workSpaces/Ocean/ssm/src/main/resources/spring/applicationContext.xml");
//        System.out.println(ctx.getBean("animal", Animal.class));

        // 使用BeanFactory容器
//        Resource resource = new ClassPathResource("spring/applicationContext.xml");
//        BeanFactory bf = new XmlBeanFactory(resource);
//        System.out.println(bf.getBean("animal", Animal.class));
//        bf.getBean("doSomeService", DoSomeServiceImpl.class).doSomeThing();


        // 动态工厂与静态工厂
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
//        System.out.println(ctx.getBean("doSomeService", IDoSomeService.class));
//        ctx.getBean("doSomeService", IDoSomeService.class).doSomeThing();
//        System.out.println(ctx.getBean("doSomeService2", IDoSomeService.class));
//        ctx.getBean("doSomeService2", IDoSomeService.class).doSomeThing();
//        System.out.println(ctx.getBean("doSomeService3", IDoSomeService.class));
//        ctx.getBean("doSomeService3", IDoSomeService.class).doSomeThing();

        // bean的作用域
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
//        Animal animal2 = ctx.getBean("animal2", Animal.class);
//        Animal animal3 = ctx.getBean("animal2", Animal.class);
//        // 容器中bean默认为单例，同一ID指向同一bean
//        System.out.println(animal2 == animal3);
//        // 不同ID对应对象不同
//        animal3 = ctx.getBean("animal3", Animal.class);
//        System.out.println(animal2 == animal3);
//        // scope作用域：prototype （创建ApplicationContext容器是不会创建prototype域的bean）
//        Animal animal4 = ctx.getBean("animal4", Animal.class);
//        Animal animal44 = ctx.getBean("animal4", Animal.class);
//        System.out.println(animal4 == animal44);

        // Bean后处理器
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
//        System.out.println(ctx.getBean("doSomeService", IDoSomeService.class));
//        System.out.println(ctx.getBean("doSomeService", IDoSomeService.class).doSomeThing());

        // Bean生命周期管理
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
//        Animal animal = ctx.getBean("animal", Animal.class);
//        System.out.println(animal);
////        ((ClassPathXmlApplicationContext)ctx).close();
//        // 如果不想在程序里直接调用close方法
//        // 则可以调用注册JVM钩子方法（shutdown hook），让程序退出是自动关闭Spring容器
//        ((AbstractApplicationContext)ctx).registerShutdownHook();

        // Spring注解管理Bean
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
//        Animal2 stu = ctx.getBean("stu", Animal2.class);
//        System.out.println(stu);

        // AOP编程
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
//        IDoSomeService doSomeService = ctx.getBean("doSomeService", IDoSomeService.class);
        // 这里要从工厂Bean获取bean，默认是JDK动态代理
//        COM.OCEAN.SSM.SPRING.SERVICE.IMPL.DOSOMESERVICEIMPL@5C7B1FF8
//        IDoSomeService doSomeService = ctx.getBean("proxyFactory", IDoSomeService.class);
//        doSomeService.getClass().getFields()
//        doSomeService.getClass().getInterfaces()
        // 如果删掉IDoSomeService，Spring默认使用CGLIG（导出子类）方式生成代理对象
//        System.out.println(doSomeService.doSomeThing());
        try {
//            System.out.println(doSomeService.doSomeThing2());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
