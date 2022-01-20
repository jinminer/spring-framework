package com.jinm.learning.aop.proxy;

import com.jinm.learning.aop.support.JMAdvisedSupport;
import com.jinm.learning.aspect.JMAdvice;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class JMJdkDynamicAopProxy implements JMAopProxy, InvocationHandler {

    private JMAdvisedSupport support;

    public JMJdkDynamicAopProxy(JMAdvisedSupport support) {

        this.support = support;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(this.support.getTargetClass().getClassLoader(), this.support.getTargetClass().getInterfaces(), this);
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Map<String, JMAdvice> advices = this.support.getAdvices(method, this.support.getTargetClass());

        return null;
    }
}