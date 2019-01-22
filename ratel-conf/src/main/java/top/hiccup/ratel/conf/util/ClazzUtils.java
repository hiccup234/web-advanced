package top.hiccup.ratel.conf.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ClassUtils;

public class ClazzUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClazzUtils.class);

    public static List<String> findClassName(String basePackage, String classPattern, ClassLoader classLoader) throws IOException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        String basePackageWithResPath = ClassUtils.convertClassNameToResourcePath(basePackage);
        String packageSearchPath = "classpath*:" + basePackageWithResPath + "/**/" + classPattern;
        Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
        if (classLoader == null) {
            classLoader = ClassUtils.getDefaultClassLoader();
        }
        List classNameList = new LinkedList();
        for (Resource res : resources) {
            LOGGER.debug(res.getURL().getPath());
            int index = res.getURL().getPath().indexOf(basePackageWithResPath);
            if (index >= 0) {
                String classNamePath = res.getURL().getPath().substring(index);
                if ("jar".equals(res.getURL().getProtocol())) {
                    int idx = classNamePath.indexOf("!");
                    if (idx != -1) {
                        classNamePath = classNamePath.substring(idx + 1);
                    }
                }
                if (classNamePath.startsWith("/")) {
                    classNamePath = classNamePath.substring(1);
                }
                String className = ClassUtils.convertResourcePathToClassName(classNamePath);
                if (className.endsWith(".class")) {
                    className = className.substring(0, className.length() - 6);
                }
                LOGGER.debug("find class: " + className);
                classNameList.add(className);
            }

        }

        return classNameList;
    }

    protected static List<String> findClassPathByBasePkg(String basePackage, ClassLoader classLoader) throws IOException {
        Enumeration resourceUrls = classLoader.getResources(
                ClassUtils.convertClassNameToResourcePath(basePackage));

        List classPathList = new LinkedList();
        while (resourceUrls.hasMoreElements()) {
            URL url = (URL) resourceUrls.nextElement();
            classPathList.add(URLDecoder.decode(url.getPath(), "utf-8"));
        }

        return classPathList;
    }

//    public static String getModuleName(Class<?> clz) {
//        if (clz.isAnnotationPresent(AppModule.class)) {
//            AppModule module = (AppModule) clz.getAnnotation(AppModule.class);
//            return module.moduleName();
//        }
//        return null;
//    }
//
//    public static Class<?> getTypeOfObjInList(Field lstField) {
//        if (!Collection.class.isAssignableFrom(lstField.getType())) {
//            throw new IllegalArgumentException("The type of argument is not a Collection type!!!");
//        }
//        if (lstField.isAnnotationPresent(AppCollectionProperty.class)) {
//            AppCollectionProperty prop = (AppCollectionProperty) lstField.getAnnotation(AppCollectionProperty.class);
//            try {
//                return Class.forName(prop.objType());
//            } catch (ClassNotFoundException e) {
//                throw new IllegalArgumentException("Can't find class by name=" + prop.objType() + " in AppCollectionProperty annotation");
//            }
//        }
//
//        Collection lst = null;
//        try {
//            lst = (Collection) lstField.get(null);
//        } catch (IllegalAccessException e) {
//            throw new IllegalArgumentException("Can't not access field value. filedName=" + lstField.getName());
//        }
//        if ((lst != null) && (!lst.isEmpty())) {
//            Object obj = lst.iterator().next();
//            return obj.getClass();
//        }
//
//        return null;
//    }
//
//    public static Class<?> getTypeOfObjInArray(Field arrField) {
//        if (!arrField.getType().isArray()) {
//            throw new IllegalArgumentException("The type of argument is not a Array type!!!");
//        }
//        if (arrField.isAnnotationPresent(AppCollectionProperty.class)) {
//            AppCollectionProperty prop = (AppCollectionProperty) arrField.getAnnotation(AppCollectionProperty.class);
//            try {
//                return Class.forName(prop.objType());
//            } catch (ClassNotFoundException e) {
//                throw new IllegalArgumentException("Can't find class by name=" + prop.objType() + " in AppCollectionProperty annotation");
//            }
//        }
//
//        Object[] arr = null;
//        try {
//            arr = (Object[]) (Object[]) arrField.get(null);
//        } catch (IllegalAccessException e) {
//            throw new IllegalArgumentException("Can't not access field value. filedName=" + arrField.getName());
//        }
//        if ((arr != null) && (arr.length > 0)) {
//            Object obj = arr[0];
//            return obj.getClass();
//        }
//
//        return null;
//    }
//
//    public static Class<?> getTypeOfKeyInMap(Field mapField) {
//        if (!Map.class.isAssignableFrom(mapField.getType())) {
//            throw new IllegalArgumentException("The type of argument is not a Collection type!!!");
//        }
//        if (mapField.isAnnotationPresent(AppMapProperty.class)) {
//            AppMapProperty prop = (AppMapProperty) mapField.getAnnotation(AppMapProperty.class);
//            try {
//                return Class.forName(prop.keyType());
//            } catch (ClassNotFoundException e) {
//                throw new IllegalArgumentException("Can't find class by name=" + prop.keyType() + " in AppMapProperty annotation");
//            }
//        }
//
//        Map map = null;
//        try {
//            map = (Map) mapField.get(null);
//        } catch (IllegalAccessException e) {
//            throw new IllegalArgumentException("Can't not access field value. filedName=" + mapField.getName());
//        }
//        if ((map != null) && (!map.isEmpty())) {
//            Object obj = map.keySet().iterator().next();
//            return obj.getClass();
//        }
//
//        return null;
//    }
//
//    public static Class<?> getTypeOfValueInMap(Field mapField) {
//        if (!Map.class.isAssignableFrom(mapField.getType())) {
//            throw new IllegalArgumentException("The type of argument is not a Collection type!!!");
//        }
//        if (mapField.isAnnotationPresent(AppMapProperty.class)) {
//            AppMapProperty prop = (AppMapProperty) mapField.getAnnotation(AppMapProperty.class);
//            try {
//                return Class.forName(prop.valueType());
//            } catch (ClassNotFoundException e) {
//                throw new IllegalArgumentException("Can't find class by name=" + prop.keyType() + " in AppMapProperty annotation");
//            }
//        }
//
//        Map map = null;
//        try {
//            map = (Map) mapField.get(null);
//        } catch (IllegalAccessException e) {
//            throw new IllegalArgumentException("Can't not access field value. filedName=" + mapField.getName());
//        }
//        if ((map != null) && (!map.isEmpty())) {
//            Object obj = map.values().iterator().next();
//            return obj.getClass();
//        }
//
//        return null;
//    }
//
//    public static Object getObjFromEnum(Class<?> enumClass, String enumConstName) {
//        int idx = enumConstName.indexOf('.');
//        String constName = null;
//        if (idx > 0) {
//            String enumClassName = enumConstName.substring(0, idx);
//            if ((!enumClass.getName().endsWith("." + enumClassName)) &&
//                    (!enumClass
//                            .getName().endsWith("$" + enumClassName))) {
//                throw new IllegalArgumentException("Invalid enumConstName parameter!!!");
//            }
//            constName = enumConstName.substring(idx + 1);
//        } else {
//            constName = enumConstName;
//        }
//
//        Object[] enumConstants = enumClass.getEnumConstants();
//        for (Object enumConstant : enumConstants) {
//            if (enumConstant.toString().equals(constName)) {
//                return enumConstant;
//            }
//        }
//        return null;
//    }
}
