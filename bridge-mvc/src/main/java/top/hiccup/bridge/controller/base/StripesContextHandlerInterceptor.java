package top.hiccup.bridge.controller.base;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * Stripes上下文拦截器
 * 桥接SpringMVC和Stripes，不影响原有SpringMVC
 * 
 * 
 * 四种思路：
 * 
 * 1.扩展mvc名称空间：扩展RequestMappingHandlerAdapter等
 * 优点是：可以将整个请求的处理流程纳入到SpringMVC
 * 缺点是：侵入性较大，SpringMVC升级后需要再做适配，技术实现比较麻烦
 * 
 * 2.扩展DispatcherServlet：重写onRefresh或者doService等
 * M 的 M-spring-support是直接创建的DispatcherServlet
 * 需要M的同学开回调入口，不太现实
 * 
 * 3.增加拦截器，没有侵入性，不影响后续SpringMVC升级，
 * 缺点是扩展性不是很好，不过对桥接需求来说已经足够（会有个问题：所有同层的其他拦截器都需要做改造）
 * 
 * 4.自定义Handlermapping，没有侵入性，理论上可以保证现有拦截器不需要改造，
 * 但是会导致SpringMVC的拦截器和Stripes的拦截器定义两套，后期维护麻烦
 *
 * @author wenhy
 * @date 2018/9/12
 */
public class StripesContextHandlerInterceptor implements HandlerInterceptor, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(StripesContextHandlerInterceptor.class);
    /**
     * 注入IOC容器
     */
    private static ApplicationContext context;
    /**
     * 当前操作系统文件分隔符
     */
    private static String separator = File.separator;
    /**
     * 保存解析的ActionBean名称
     */
    private static List<String> actionBeanNames = new ArrayList<>(256);
    /**
     * 保存解析的ActionBean
     */
    private static List<Class<? extends Object>> actionBeanclasses = new ArrayList<>(256);
    /**
     * 保存映射到的请求路径
     */
    private static Map<String, Object> handlerMapping = new ConcurrentHashMap<>(256);
    /**
     * Stripes的ActionBean路径，多个base-package以';'分隔即可
     */
    private static String basePackages = "com.wlqq.insurance.actionbean;com.wlqq.insurance.api;com.wlqq.insurance.ymm";

    static {
        LOGGER.info("StripesContextHandlerInterceptor <clinit> begin");
        long startTime = System.currentTimeMillis();
        scanBasePackage(basePackages);
        initHandlerMappings();
        LOGGER.info("StripesContextHandlerInterceptor <clinit> completed: {}", System.currentTimeMillis() - startTime);
    }

    /**
     * 在Tomcat环境下读取classpath调用这个方法
     * M mock时也调用这个方法（如果以M mock启动，实际的classpath是target路径）
     *
     * @param basePackage
     */
    private static void doScanClasspath(String basePackage) {
        // 把给定包路径中所有的.替换成/
        URL url = StripesContextHandlerInterceptor.class.getClassLoader().getResource(separator + basePackage.replaceAll("\\.", "/"));
        String filePath = null;
        try {
            // 编码文件路径中可能存在的空格等其他字符
            filePath = URLDecoder.decode(url.getFile(), "UTF-8");
            File dir = new File(filePath);
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    // 递归扫描包路径下的class文件
                    doScanClasspath(basePackage + "." + file.getName());
                } else {
                    String className = basePackage + "." + file.getName().replace(".class", "");
                    actionBeanNames.add(className);
                    actionBeanclasses.add(Class.forName(className));
                }
            }
        } catch (UnsupportedEncodingException | ClassNotFoundException e) {
            LOGGER.error("Parse actionBean fail:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 以M方式启动时调用这个方法（持续继承环境CI以jar的方式部署module）
     *
     * @param basePackage
     */
    private static void doScanJarPath(String basePackage) {
        try {
            String classpaths = System.getProperty("java.class.path");
            LOGGER.info("classPaths: {}", classpaths);
            String classpath = null;
            String[] classpathArr = {};
            // Newton里classpaths以 : 分割
            if (classpaths.contains(":")) {
                classpathArr = classpaths.split(":");
            } else {
                classpathArr = classpaths.split(";");
            }
            for (String path : classpathArr) {
                // 排除掉fis-server-bridge
                if (path.contains("XXX") && !path.contains("XXX-bridge")) {
                    classpath = path;
                }
            }
            // 编码文件路径中可能存在的空格等其他字符
            classpath = URLDecoder.decode(classpath, "UTF-8");
            LOGGER.info("fis-server path: {}", classpath);
            // 把给定的基本包路径中所有的.替换成系统文件分隔符
            String pathName = basePackage.replaceAll("\\.", "/");
            LOGGER.info("pathName: {}", pathName);
            if (classpath.endsWith(".jar")) {
                JarFile jarFile = new JarFile(classpath);
                Enumeration<JarEntry> files = jarFile.entries();
                LOGGER.info("files: {}", files);
                while (files.hasMoreElements()) {
                    JarEntry jarEntry = files.nextElement();
                    String entryName = jarEntry.getName();
                    LOGGER.info("entryName: {}", entryName);
                    if (entryName.contains(pathName) && entryName.endsWith(".class")) {
                        String className = entryName.replaceAll("/", ".");
                        LOGGER.info("className: {}", className);
                        className = className.substring(0, className.length() - 6);
                        actionBeanNames.add(className);
                        actionBeanclasses.add(Class.forName(className));
                    }
                }
            } else {
                doScanClasspath(basePackage);
            }
        } catch (ClassNotFoundException | IOException e) {
            LOGGER.error("Parse actionBean fail: " + basePackages, e);
            throw new RuntimeException("Parse actionBean fail: " + basePackages, e);
        }
    }

    /**
     * 扫描给定基础路径下的所有Stripes ActionBean
     *
     * @param basePackages
     */
    protected static void scanBasePackage(String basePackages) {
        LOGGER.info("Parse actionBean begin");
        if (null != basePackages) {
            for (String basePackage : basePackages.split(";")) {
                doScanJarPath(basePackage);
            }
        }
        LOGGER.info("Parse actionBean end");
    }

    private static void initHandlerMappings() {
        for (Class clazz : actionBeanclasses) {
            // 如果类上没有UrlBinding注解则不解析
            if (!clazz.isAnnotationPresent(UrlBinding.class)) {
                continue;
            }
            UrlBinding urlBinding = (UrlBinding) clazz.getAnnotation(UrlBinding.class);
            String baseUrl = urlBinding.value();
            if (baseUrl.endsWith(".do")) {
                handlerMapping.put(baseUrl, clazz);
                LOGGER.info("Mapped: " + baseUrl + " ==> " + clazz);
            } else {
                Method[] methods = clazz.getMethods();
                if (null != methods) {
                    for (Method method : methods) {
                        if (!method.isAnnotationPresent(HandlesEvent.class)) {
                            continue;
                        }
                        HandlesEvent handlesEvent = method.getAnnotation(HandlesEvent.class);
                        String url = handlesEvent.value();
                        url = (baseUrl + "/" + url).replaceAll("/+", "/");
                        handlerMapping.put(url, method);
                        LOGGER.info("Mapped: " + url + " ==> " + method);
                    }
                }
            }
        }
    }


    /**
     * 拦截所有请求，如果能映射到Stripes则直接派发给Stripes并return false;
     * 如果不能映射则回退给SpringMVC
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if (handlerMapping.isEmpty()) {
            return true;
        }
        String url = httpServletRequest.getRequestURI();
        String contextPath = httpServletRequest.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        LOGGER.info("当前请求路径: {}", url);
        if (!StripesContextHandlerInterceptor.handlerMapping.containsKey(url)) {
            // 交给原生Spring MVC
            LOGGER.info("请求由SpringMVC处理: {}", url);
            return true;
        }
        try {
            LOGGER.info("请求由Stripes处理: {}", url);
            StripesContextHolder.doService(httpServletRequest.getServletContext(), getApplicationContext(),
                    httpServletRequest, httpServletResponse);
            return false;
        } catch (Exception e) {
            LOGGER.error("处理请求异常：", e);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    private static void checkApplicationContext() {
        if (null == context) {
            throw new IllegalStateException("The ApplicationContext has not initialized!");
        }
    }

    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return context;
    }
}
