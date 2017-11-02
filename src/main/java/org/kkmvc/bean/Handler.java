package org.kkmvc.bean;


import lombok.Generated;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * 封装Action信息
 */
@Generated
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


}
