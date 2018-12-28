package top.hiccup.bridge.controller.base;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

import org.springframework.util.Assert;

/**
 * 手动配置StripesFilterConfig
 *
 * @author wenhy
 * @date 2018/9/15
 */
public class StripesFilterConfig implements FilterConfig {

    private final ServletContext servletContext;

    private final String filterName;

    private Map<String, String> initParameters;

    public StripesFilterConfig(String filterName, ServletContext servletContext) {
        this.filterName = filterName;
        this.servletContext = servletContext;
        this.initParameters = getDefaultInitParameters();
    }

    public StripesFilterConfig(String filterName, ServletContext servletContext, Map<String, String> initParameters) {
        this.filterName = filterName;
        this.servletContext = servletContext;
        this.initParameters = initParameters;
    }

    private Map<String, String> getDefaultInitParameters() {
        Map<String, String> initParameters = new LinkedHashMap<>();
        // XXXXXX填写web.xml中配置的路径即可
        initParameters.put("ActionResolver.Packages", "XXXXXX");
        initParameters.put("Interceptor.Classes",
                "net.sourceforge.stripes.controller.BeforeAfterMethodInterceptor," +
                "net.sourceforge.stripes.integration.spring.SpringInterceptor,");
        initParameters.put("MultipartWrapper.Class", "net.sourceforge.stripes.controller.multipart.CommonsMultipartWrapper");
        initParameters.put("FileUpload.MaximumPostSize", "10mb");
        initParameters.put("ExceptionHandler.Class", "net.sourceforge.stripes.exception.DelegatingExceptionHandler");
        initParameters.put("LocalizationBundleFactory.FieldNameBundle", "ApplicationResources");
        initParameters.put("LocalizationBundleFactory.ErrorMessageBundle", "ApplicationResources");
        initParameters.put("LocalePicker.Locales", "zh_CN:UTF-8,en_US:UTF-8");
        return initParameters;
    }


    @Override
    public String getFilterName() {
        return filterName;
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