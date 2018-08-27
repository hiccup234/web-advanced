package com.hiccup.mvc;

import com.hiccup.mvc.annotation.Controller;
import com.hiccup.mvc.annotation.RequestMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * SpringMVC本质上是一个Servlet，继承自HttpServlet。
 * FrameworkServlet负责初始化SpringMVC的IOC容器
 *
 * ⑴ 用户发送请求至前端控制器DispatcherServlet
 * ⑵ DispatcherServlet收到请求调用HandlerMapping处理器映射器
 * ⑶ 处理器映射器根据请求url找到具体的处理器，生成处理器对象及处理器拦截器(如果有则生成)一并返回给DispatcherServlet
 * ⑷ DispatcherServlet通过HandlerAdapter处理器适配器调用处理器
 * ⑸ 执行处理器(Controller，也叫控制器)。
 * ⑹ Controller执行完成返回ModelAndView
 * ⑺ HandlerAdapter将controller执行结果ModelAndView返回给DispatcherServlet
 * ⑻ DispatcherServlet将ModelAndView传给ViewReslover视图解析器
 * ⑼ ViewReslover解析后返回具体View
 * ⑽ DispatcherServlet对View进行渲染视图（即将模型数据填充至视图中）
 * ⑾ DispatcherServlet响应用户
 *
 *
 * @author wenhy
 * @date 2018/8/22
 */
public class DispatcherServlet extends HttpServlet {

//    /**
//     * protected void initStrategies(ApplicationContext context) {
//     * //用于处理上传请求。处理方法是将普通的request包装成            MultipartHttpServletRequest，后者可以直接调用getFile方法获取File.
//     *  initMultipartResolver(context);
//     * //SpringMVC主要有两个地方用到了Locale：一是ViewResolver视图解析的时候；二是用到国际化资源或者主题的时候。
//     *  initLocaleResolver(context);
//     * //用于解析主题。SpringMVC中一个主题对应 一个properties文件，里面存放着跟当前主题相关的所有资源、//如图片、css样式等。SpringMVC的主题也支持国际化，
//     *  initThemeResolver(context);
//     * //用来查找Handler的。
//     *  initHandlerMappings(context);
//     * //从名字上看，它就是一个适配器。Servlet需要的处理方法的结构却是固定的，都是以request和response为参数的方法。//如何让固定的Servlet处理方法调用灵活的Handler来进行处理呢？这就是HandlerAdapter要做的事情
//     *  initHandlerAdapters(context);
//     * //其它组件都是用来干活的。在干活的过程中难免会出现问题，出问题后怎么办呢？//这就需要有一个专门的角色对异常情况进行处理，在SpringMVC中就是HandlerExceptionResolver。
//     *  initHandlerExceptionResolvers(context);
//     * //有的Handler处理完后并没有设置View也没有设置ViewName，这时就需要从request获取ViewName了，//如何从request中获取ViewName就是RequestToViewNameTranslator要做的事情了。
//     *  initRequestToViewNameTranslator(context);
//     * //ViewResolver用来将String类型的视图名和Locale解析为View类型的视图。//View是用来渲染页面的，也就是将程序返回的参数填入模板里，生成html（也可能是其它类型）文件。
//     *  initViewResolvers(context);
//     * //用来管理FlashMap的，FlashMap主要用在redirect重定向中传递参数。
//     *  initFlashMapManager(context);
//     * }
//     */

    private Properties properties = new Properties();

    private List<String> classNames = new ArrayList<>();
    /**
     * 模拟IOC容器
     */
    private Map<String, Object> ioc = new HashMap<>();

    private Map<String, Method> handlerMapping = new  HashMap<>();

    private Map<String, Object> controllerMap  =new HashMap<>();

    /**
     * 测试init执行失败情况
     */
//    private int count = 0;
    private static int count = 0;

    /**
     * 如果第一次执行init失败的话，以后每次请求进来都会重新实例化该Servlet并执行init方法，直到成功为止
     * @param servletConfig
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("中央调度器启动：" + servletConfig.getServletName());
        ServletContext servletContext = servletConfig.getServletContext();
        System.out.println(servletContext);
        System.out.println(this);
        if (count < 5) {
            count++;
            throw new RuntimeException("假装启动失败..");
        }

        // 1.加载web.xml中指定的配置文件
        doLoadConfig(servletConfig.getInitParameter("propertiesLocation"));

        // 2.扫描用户给定的包下面所有的类
        doScanner(properties.getProperty("base-package"));

        // 3.装载前面扫描到的类，通过反射机制实例化并放到ioc容器中(k-v  beanName-bean) beanName默认是首字母小写
        doInstance();

        // 4.初始化HandlerMapping(将url和method对应上)
        initHandlerMapping();

    }

    private void  doLoadConfig(String location){
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(location);
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != resourceAsStream){
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        try (InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(location)) {
//            properties.load(resourceAsStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void doScanner(String packageName) {
        // 把给定包路径中所有的.替换成/
        URL url  =this.getClass().getClassLoader().getResource("/"+packageName.replaceAll("\\.", "/"));
        String filePath = null;
        try {
            filePath = URLDecoder.decode(url.getFile(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        File dir = new File(filePath);
        for (File file : dir.listFiles()) {
            if(file.isDirectory()){
                // 递归扫描包路径下的class文件
                doScanner(packageName+"."+file.getName());
            }else{
                String className = packageName +"." +file.getName().replace(".class", "");
                classNames.add(className);
            }
        }
    }

    private void doInstance() {
        if (classNames.isEmpty()) {
            return;
        }
        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(Controller.class)){
                    ioc.put(toLowerFirstChar(clazz.getSimpleName()), clazz.newInstance());
                } else {
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String toLowerFirstChar(String name){
        char[] charArray = name.toCharArray();
        charArray[0] += 32;
        return String.valueOf(charArray);
    }

    private void initHandlerMapping(){
        if (ioc.isEmpty()){
            return;
        }
        try {
            for (Map.Entry<String, Object> entry : ioc.entrySet()) {
                Class<? extends Object> clazz = entry.getValue().getClass();
                if(!clazz.isAnnotationPresent(Controller.class)){
                    continue;
                }
                String baseUrl = "";
                if(clazz.isAnnotationPresent(RequestMapping.class)){
                    RequestMapping annotation = clazz.getAnnotation(RequestMapping.class);
                    baseUrl = annotation.value();
                }
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if(!method.isAnnotationPresent(RequestMapping.class)){
                        continue;
                    }
                    RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                    String url = annotation.value();
                    url =(baseUrl + "/" + url).replaceAll("/+", "/");
                    handlerMapping.put(url, method);
                    controllerMap.put(url, entry.getValue());
                    System.out.println("Has mapped: " + url +"," + method);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.doDispatch(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Server Exception");
        }

    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if(handlerMapping.isEmpty()){
            return;
        }
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        if(!this.handlerMapping.containsKey(url)){
            resp.getWriter().write("404 NOT FOUND!");
            return;
        }
        Method method =this.handlerMapping.get(url);
        // 获取方法的参数列表
        Class<?>[] parameterTypes = method.getParameterTypes();
        // 获取请求的参数
        Map<String, String[]> parameterMap = req.getParameterMap();
        Object [] paramValues= new Object[parameterTypes.length];

        //方法的参数列表
        for (int i = 0; i<parameterTypes.length; i++){
            //根据参数名称，做某些处理
            String parameterType = parameterTypes[i].getSimpleName();
            if (parameterType.equals("HttpServletRequest")){
                //参数类型已明确，这边强转类型
                paramValues[i] = req;
                continue;
            }
            if (parameterType.equals("HttpServletResponse")){
                paramValues[i] = resp;
                continue;
            }
            if (parameterType.equals("String")){
                for (Map.Entry<String, String[]> param : parameterMap.entrySet()) {
                    String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
                    paramValues[i] = value;
                }
            }
        }
        try {
            // 第一个参数为method所对应的实例对象
            Object returnObject = method.invoke(this.controllerMap.get(url), paramValues);
            resp.getWriter().print(returnObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
