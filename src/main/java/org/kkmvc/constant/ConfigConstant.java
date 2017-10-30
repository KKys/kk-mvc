package org.kkmvc.constant;

/**
 * 配置项常量
 */
public interface ConfigConstant {
    String CONFIG_FILE_NAME = "kkmvc.properties";

    String JDBC_DRIVER = "kkmvc.framework.jdbc.driver";
    String JDBC_URL = "kkmvc.framework.jdbc.url";
    String JDBC_USERNAME = "kkmvc.framework.jdbc.username";
    String JDBC_PASSWORD = "kkmvc.framework.jdbc.password";

    String APP_BASE_PACKAGE = "kkmvc.framework.app.base_package";
    String APP_JSP_PATH = "kkmvc.framework.app.jsp_path";
    String APP_ASSET_PATH = "kkmvc.framework.app.assert_path";




    /**
     * kkmvc.framework.jdbc.driver=
     kkmvc.framework.jdbc.url=jdbc:mysql://121.42.8.85:3306/kk-mvc
     kkmvc.framework.jdbc.username=KKys
     kkmvc.framework.jdbc.password=ysysycyc

     kkmvc.framework.app.base_package=org.kkmvcTest
     kkmvc.framework.app.jsp_path=/WEB-INF/view/
     kkmvc.framework.app.assert_path=/assert/

     */
}
