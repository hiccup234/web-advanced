package top.hiccup.bridge.controller.base;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.springframework.util.Assert;

/**
 * 手动配置StripesServletConfig
 *
 * @author wenhy
 * @date 2018/9/15
 */
public class StripesServletConfig implements ServletConfig {

    private final ServletContext servletContext;

    private final String servletName;

    private Map<String, String> initParameters;

    public StripesServletConfig(String servletName, ServletContext servletContext) {
        this.servletName = servletName;
        this.servletContext = servletContext;
        this.initParameters = getDefaultInitParameters();
    }

    public StripesServletConfig(String servletName, ServletContext servletContext, Map<String, String> initParameters) {
        this.servletName = servletName;
        this.servletContext = servletContext;
        this.initParameters = initParameters;
    }

    private Map<String, String> getDefaultInitParameters() {
        Map<String, String> initParameters = new LinkedHashMap<>();
        initParameters.put("load-on-startup", "1");
        return initParameters;
    }

    @Override
    public String getServletName() {
        return servletName;
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public String getInitParameter(String name) {
        Assert.notNull(name, "Parameter name must not be null");
        return this.initParameters.get(name);
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return Collections.enumeration(this.initParameters.keySet());
    }
}
