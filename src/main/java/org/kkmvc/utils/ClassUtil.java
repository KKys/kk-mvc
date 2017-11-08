package org.kkmvc.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类操作工具类
 */
public final class ClassUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     */
    public static ClassLoader getClassloader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     * 为提高性能（不做初始化操作），isInitialized一般为false
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassloader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class fail", e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    /**
     * 获取指定包下的所有类
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            //获取所有文件的url地址
            Enumeration<URL> urls = getClassloader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocal = url.getProtocol();
                    if (protocal.equals("file")) {//如果文件类型是file
                        //处理空格
                        String packagePath = url.getPath().replaceAll("%20", "");
                        addClass(classSet, packagePath, packageName);
                    } else if (protocal.equals("jar")) {//如果文件类型是jar
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile != null) {
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                //循环jar里面的所有.class文件
                                while (jarEntries.hasMoreElements()) {
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")) {
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                        doAddClass(classSet, className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("get class set failure", e);
            throw new RuntimeException(e);
        }

        return classSet;
    }

    /**
     * @param classSet    最后要存放的classSet
     * @param packagePath 文件所在的文件夹目录
     * @param packageName
     */
    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                //判断以class结尾的或者是文件夹的返回，进行下面的操作
                return (file.isFile() && file.getName().endsWith(".class") || file.isDirectory());
            }
        });
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {//如果是文件，也就是class结尾的
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtils.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {//如果是文件夹
                String subPackagePath = fileName;
                if (StringUtils.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtils.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }
}
