//package top.hiccup.ratel.conf;
//
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.lang.reflect.Type;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Properties;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.Assert;
//import org.springframework.util.ClassUtils;
//
//import top.hiccup.ratel.conf.util.ClazzUtils;
//import top.hiccup.ratel.conf.util.ReflectionUtils;
//
///**
// * 写常量工具类
// *
// * @author wenhy
// * @date 2019/1/22
// */
//public class WriteConstantHelper {
//    private static Logger log = LoggerFactory.getLogger(WriteConstantHelper.class);
//
//    public static void writeConstant(Properties props) throws IOException {
//        String domain = (String) PropertyHolder.getProperties("uniconfig.domain");
//        Assert.notNull(domain, "domain of UniConfigPropertyLoader must not be null!");
//
//        String basePkg = (String) PropertyHolder.getProperties("constantclass.basepackage");
//        Assert.notNull(basePkg, "basePkg of UniConfigPropertyLoader must not be null!");
//
//        String classPattern = (String) PropertyHolder.getProperties("constantclass.pattern");
//        Assert.notNull(classPattern, "classPattern of UniConfigPropertyLoader must not be null!");
//
//        writeConstant(null, props, domain, null, basePkg, classPattern);
//    }
//
//    public static void writeConstant(ClassLoader classLoader, Properties props, String domain, String modName, String basePkg, String classPattern) throws IOException {
//        log.info("props = {}", props.toString());
//        if ((props == null) || (props.isEmpty())) {
//            return;
//        }
//        if (classPattern == null) {
//            return;
//        }
//        List<String> constClassNameList = new LinkedList();
//
//        if (classLoader == null) {
//            classLoader = ClassUtils.getDefaultClassLoader();
//        }
//        long beginTime = System.currentTimeMillis();
//
//        String[] classPatterns = classPattern.split(",");
//        for (String clzPattern : classPatterns) {
//            List constClzNameList = ClazzUtils.findClassName(basePkg, clzPattern, classLoader);
//            if ((constClzNameList != null) && (!constClzNameList.isEmpty())) {
//                constClassNameList.addAll(constClzNameList);
//            }
//        }
//        long endTime = System.currentTimeMillis();
//        log.info("Cost time {} seconds while search constant class in module{}",
//                Long.valueOf((endTime - beginTime) / 1000L),
//                modName);
//        if (!constClassNameList.isEmpty())
//            log.info("Yes! find constClassNameList:" + constClassNameList);
//        else {
//            log.warn("Warning! Can't find any constant class! Pls check {} and {} in local property file!!!", basePkg, classPattern);
//        }
//        for (String constClassName : constClassNameList) {
//            Class consClass = null;
//            try {
//                consClass = classLoader.loadClass(constClassName);
//            } catch (ClassNotFoundException e) {
//                log.error("Can't load class by name {} !", constClassName, e);
//            }
//            continue;
//
//            String moduleName = modName != null ? modName : ClazzUtils.getModuleName(consClass);
//
//            log.info("begining to write Constant[{}] in module[{}]", constClassName, moduleName == null ? "" : moduleName);
//
//            Field[] fields = consClass.getFields();
//            for (Field field : fields) {
//                String propKey = generateWholePropKey(domain, moduleName, field.getName());
//                String propValue = props.getProperty(propKey);
//                log.info("propKey={},propValue={}", propKey, propValue);
//
//                if ((propValue == null) && (moduleName == null)) {
//                    propValue = props.getProperty(field.getName());
//                }
//
//                if (propValue == null) continue;
//                try {
//                    Class clazz = field.getType();
//                    log.info("clazz={},field={}", clazz.getName(), field.getName());
//                    boolean isPrimitive = ClassUtils.isPrimitiveOrWrapper(clazz);
//                    Object val = null;
//                    if (clazz == String.class) {
//                        val = propValue;
//                    } else if (!isPrimitive) {
//                        Type type = field.getGenericType();
//                        val = JsonUtil.getJsonConverter().toBean(propValue, clazz, type);
//                    } else {
//                        val = JsonUtil.getJsonConverter().toBean(propValue, clazz);
//                    }
//
//                    if (clazz.isAssignableFrom(List.class)) {
//                        List oldList = (List) field.get(null);
//                        oldList.clear();
//                        oldList.addAll((List) val);
//                    } else {
//                        ReflectionUtils.setFieldValue(field, val);
//                    }
//
//                } catch (Exception e) {
//                    String errMsg = String.format("从常量类[ %s]取[%s]的值或写值时发生异常", new Object[]{constClassName, field
//                            .getName()});
//                    log.error(errMsg, e);
//                }
//            }
//        }
//    }
//
//    public static String generateWholePropKey(String domain, String moduleName, String rawKey) {
//        StringBuilder key = new StringBuilder();
//        if ((domain != null) && (!"".equals(domain))) {
//            key.append(domain + ".");
//        }
//        if ((moduleName != null) && (!"".equals(moduleName))) {
//            key.append(moduleName + ".");
//        }
//        key.append(rawKey);
//        return key.toString();
//    }
//}
