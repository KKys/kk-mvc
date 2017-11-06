package org.kkmvc.bean;

import lombok.Getter;

/**
 * 返回数据对象
 */
public class Data {

    /**
     * 模型数据
     */
    @Getter
    private Object model;

    public Data(Object model) {
        this.model = model;
    }
}
