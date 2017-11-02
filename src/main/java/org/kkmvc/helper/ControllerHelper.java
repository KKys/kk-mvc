package org.kkmvc.helper;

import org.kkmvc.bean.Handler;
import org.kkmvc.bean.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * 控制器助手类
 */
public class ControllerHelper {

    /**
     * 用于存放请求与controller的映射关系
     */
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {

    }
}
