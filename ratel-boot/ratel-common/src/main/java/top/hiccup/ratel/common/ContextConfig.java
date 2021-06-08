package top.hiccup.ratel.common;

/**
 * 应用上下文配置
 *
 * @author wenhy
 * @date 2021/6/7
 */
@Slf4j
@EnableScheduling
@EnableAsync(proxyTargetClass = true)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableConfigurationProperties(ForgeCommonProperties.class)
public class ContextConfig implements AsyncConfigurer, InitializingBean, DisposableBean {

}
