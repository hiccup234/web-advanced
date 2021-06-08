package top.hiccup.ratel.rpc.dubbo.apache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
@Validated
@ConfigurationProperties(prefix = "forge.rpc.dubbo.apache")
public class DubboProperties {

    @NotNull
    private Boolean enable;

    @NotEmpty
    private String address;

    @Min(20880)
    private Integer port;

    @Min(200)
    private Integer threadCount;

    @Min(5000)
    private Integer timeoutMillis;

    private List<RegistryBeanConfig> providers;

    private List<RegistryBeanConfig> consumers;

    @Data
    public static class RegistryBeanConfig {

        @NotNull
        private Class<?> api;

        private String version; //default 1.0.0

        private String filters;

        private Map<String, String> parameters;
    }
}
