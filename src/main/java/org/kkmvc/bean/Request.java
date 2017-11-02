package org.kkmvc.bean;

import lombok.Generated;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 封装请求信息
 */
@Generated
public class Request {
    /**
     * 请求方法
     */
    @Getter
    private String requestMethod;

    /**
     * 请求路径
     */
    @Getter
    private String requestPath;

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
