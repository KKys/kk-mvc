package org.kkmvc;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.kkmvc.bean.Data;
import org.kkmvc.bean.Handler;
import org.kkmvc.bean.Param;
import org.kkmvc.bean.View;
import org.kkmvc.helper.BeanHelper;
import org.kkmvc.helper.ControllerHelper;
import org.kkmvc.helper.HelperLoader;
import org.kkmvc.utils.ConfigUtil;
import org.kkmvc.utils.ReflectionUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
 *
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
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求方法与请求路径
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();
        //获取Action处理器
        Handler handler = ControllerHelper.getHandler(requestMethod,requestPath);
        if(handler != null){
            //获取Controller类及其Bean实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            //创建请求参数对象
            Map<String,Object> paramMap = new HashMap<String, Object>();
            Enumeration<String> paramNames = request.getParameterNames();
            //循环names，取参数values
            while (paramNames.hasMoreElements()){
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                paramMap.put(paramName,paramValue);
            }
            //获取请求体
            String body = CodecUtil.decodeUrl(StreamUtil.getString(request.getInputStream()));
            if(StringUtils.isNotEmpty(body)){
                //分割请求体的参数
                String[] params = StringUtils.split(body,"&");
                if(ArrayUtils.isNotEmpty(params)){
                    for(String param:params){
                        //分割参数的key/value
                        String[] array = StringUtils.split(param,"=");
                        if(ArrayUtils.isNotEmpty(array)&&array.length==2){
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName,paramValue);
                        }
                    }
                }
            }
            //构造请求bean
            Param param = new Param(paramMap);
            //调用Action方法
            Method actionMethod = handler.getActionMethod();
            //获取返回值
            Object result = ReflectionUtil.invokeMethod(controllerBean,actionMethod,param);
            //处理action方法返回值
            if(result instanceof View){
                //返回JSP页面
                View view = (View)result;
                String path = view.getPath();
                if(StringUtils.isNotEmpty(path)){
                    //如果url以/开头，说明是重定向到某方法
                    if(path.startsWith("/")){
                        response.sendRedirect(request.getContextPath()+path);
                    }else {
                        //不是重定向的请求，需要添加model信息
                        Map<String,Object> model = view.getModel();
                        for(Map.Entry<String,Object> entry:model.entrySet()){
                            request.setAttribute(entry.getKey(),entry.getValue());
                        }
                        //转发到某jsp页面
                        request.getRequestDispatcher(ConfigUtil.getAppJspPath()+path).forward(request,response);
                    }
                }
            }else if(result instanceof Data){
                //返回json数据
                Data data = (Data) result;
                Object model = data.getModel();
                if (model!=null){
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter writer = response.getWriter();
                    String json = JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        }
    }
}
