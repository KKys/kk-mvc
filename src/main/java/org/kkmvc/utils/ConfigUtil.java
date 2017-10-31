package org.kkmvc.utils;

import org.kkmvc.constant.ConfigConstant;

import java.io.InputStream;
import java.util.Properties;

/**
 * 解析配置类
 */
public final class ConfigUtil {
    private static final Properties CONFIG_PROPERTIES = load(ConfigConstant.CONFIG_FILE_NAME);

    /**
     * 加载配置类
     */
    private static Properties load(String propertiesPath) {
        // 生成输入流
        InputStream ins = ConfigUtil.class.getResourceAsStream(propertiesPath);
        // 生成properties对象
        Properties p = new Properties();
        try {
            p.load(ins);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    /**
     * 获取 jdbc 驱动
     */
    public static String getJdbcDriver() {
        return CONFIG_PROPERTIES.getProperty(ConfigConstant.JDBC_DRIVER, "com.mysql.jdbc.Driver");
    }

    /**
     * 获取 jdbc url
     */
    public static String getJdbcUrl() {
        return CONFIG_PROPERTIES.getProperty(ConfigConstant.JDBC_URL);
    }

    /**
     * 获取 jdbc 用户名
     */
    public static String getJdbcUsername() {
        return CONFIG_PROPERTIES.getProperty(ConfigConstant.JDBC_USERNAME);
    }

    /**
     * 获取 jdbc 密码
     */
    public static String getJdbcPassword() {
        return CONFIG_PROPERTIES.getProperty(ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * 获取基础包名
     */
    public static String getAppBasePackage() {
        return CONFIG_PROPERTIES.getProperty(ConfigConstant.APP_BASE_PACKAGE);
    }

    /**
     * 获取 jsp 路径
     */
    public static String getAppJspPath() {
        return CONFIG_PROPERTIES.getProperty(ConfigConstant.APP_JSP_PATH, "/WEB-INF/view");
    }

    /**
     * 获取 静态资源 路径
     */
    public static String getAppAssetPath() {
        return CONFIG_PROPERTIES.getProperty(ConfigConstant.APP_ASSET_PATH, "/asset/");
    }

    public static void main(String[] args) {
        // 生成输入流
        InputStream ins = ConfigUtil.class.getResourceAsStream("/log4j.properties");
        // 生成properties对象
        Properties p = new Properties();
        try {
            p.load(ins);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 输出properties文件的内容
        System.out.println("name:" + p.getProperty("log4j.rootLogger"));
    }
}
