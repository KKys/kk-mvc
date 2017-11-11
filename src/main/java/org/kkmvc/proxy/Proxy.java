package org.kkmvc.proxy;

/**
 * 代理接口
 */
public interface Proxy {

    /**
     * 执行链式代理(把多个代理串起来，一个个去执行，有先后顺序)
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
