package org.kkmvc.bean;

import lombok.Getter;

import java.util.Map;

/**
 * 请求参数对象
 */
public class Param {

    @Getter
    private Map<String,Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
}
