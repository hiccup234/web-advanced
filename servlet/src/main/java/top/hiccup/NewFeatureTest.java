package top.hiccup;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet3主要增加了以下特性:
 * <p>
 * 1、异步处理支持
 * 2、可插性支持
 * 3、注解支持(零配置)可不用配置web.xml 【javax.servlet.ServletContainerInitializer】
 *
 * @author wenhy
 * @date 2018/12/31
 */
@WebServlet(name = "index", urlPatterns = {"/"}, asyncSupported = true)
public class NewFeatureTest extends HttpServlet {

    /**
     *  通过方法可以看出servlet立马返回了，但没有关闭响应流，只是把response响应传给了异步线程，异步线程再继续输出，
     *  我们可以将比较费资源消耗时间的程序放到异步线程去处理，这样很大程度上节省了servlet资源。
     *  Springmvc3.2开始也加入了servlet3异步处理这个特性
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        try {
            PrintWriter out = resp.getWriter();
            out.println("servlet started.<br/>");
            out.flush();
            AsyncContext asyncContext = req.startAsync();
            asyncContext.addListener(getListener());
            asyncContext.start(new IndexThread(asyncContext));
            out.println("servlet end.<br/>");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步线程结果监听
     *
     * @return
     * @author javastack
     */
    private AsyncListener getListener() {
        return new AsyncListener() {

            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
                asyncEvent.getSuppliedResponse().getWriter().close();
                System.out.println("thread completed.");
            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {
                System.out.println("thread error.");
            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
                System.out.println("thread started.");
            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                System.out.println("thread timeout.");
            }
        };
    }
}

class IndexThread implements Runnable {

    private AsyncContext asyncContext;

    public IndexThread(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
            PrintWriter out = asyncContext.getResponse().getWriter();
            out.println("hello servlet3.<br/>");
            out.flush();
            asyncContext.complete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
