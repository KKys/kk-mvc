package org.kkmvc.bean;


import lombok.Generated;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * 封装Action信息
 */

public class Handler {
    /**
     * controller类
     */
    @Getter
    private Class<?> controllerClass;

    /**
     * Action方法
     */
    @Getter
    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }
}
