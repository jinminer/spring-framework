package com.jinm.learning.aop.proxy;

public interface JMAopProxy {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);

}
