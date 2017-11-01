package org.kkmvc.helper;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.kkmvc.annotation.Inject;
import org.kkmvc.utils.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public final class IocHelper {
    static {
        //获取所有的bean类与bean实例之间的映射关系
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtils.isNotEmpty((Collection<?>) beanMap)) {
            //遍历bean map
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                //从beanMap中获取Bean类与bean实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                //获取bean类定义的所有成员变量
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)) {
                    //遍历bean field
                    for (Field beanField : beanFields) {
                        //判断当前bean field是否带有Inject注解
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            //在bean map中获取beanField对应实例
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                //通过反射初始化beanField的值
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
