package top.hiccup.ratel.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 将常量信息写到常量类中
 *
 * @author wenhy
 * @date 2019/1/22
 */
public class WriteConstListener {
    private static Logger log = LoggerFactory.getLogger(WriteConstListener.class);

    public void notify(String moduleName, ClassLoader classLoader) {
        log.info("begin to write constant...");
        String domain = (String) PropertyHolder.getProperties("uniconfig.domain");
        String basePkg = (String) PropertyHolder.getProperties("constantclass.basepackage");
        String classPattern = (String) PropertyHolder.getProperties("constantclass.pattern");
        WriteConstantHelper.writeConstant(this.val$classLoader, PropertyHolder.getProperties(), domain, this.val$moduleName, basePkg, classPattern);

        log.info("end to write constant...");
    }
}
