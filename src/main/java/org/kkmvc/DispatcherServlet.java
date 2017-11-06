package org.kkmvc;

import org.kkmvc.helper.HelperLoader;
import org.kkmvc.utils.ConfigUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求转发器
 * <p>
 * 1)load-on-startup元素标记容器是否在启动的时候就加载这个servlet(实例化并调用其init()方法)。
 * <p>
 * 2)它的值必须是一个整数，表示servlet应该被载入的顺序
 * <p>
 * 2)当值为0或者大于0时，表示容器在应用启动时就加载并初始化这个servlet；
 * <p>
 * 3)当值小于0或者没有指定时，则表示容器在该servlet被选择时才会去加载。
 * <p>
 * 4)正数的值越小，该servlet的优先级越高，应用启动时就越先加载。
 * <p>
 * 5)当值相同时，容器就会自己选择顺序来加载。
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {


    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化相关Helper类
        HelperLoader.init();
        //获取ServletContext对象，用于注册Servlet
        ServletContext servletContext = config.getServletContext();
        //注册处理JSP的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigUtil.getAppJspPath() + "*");
        //注册处理静态资源的默认Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigUtil.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }
}
