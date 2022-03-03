package com.jinm.learning.aop.proxy;

import com.jinm.learning.aop.support.JMAdvisedSupport;
import com.jinm.learning.aspect.JMAdvice;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
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
        // 生成的$Proxy0源码
        // -Dsun.misc.ProxyGenerator.saveGeneratedFiles=true
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
//        System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");

        return Proxy.newProxyInstance(this.support.getTargetClass().getClassLoader(), this.support.getTargetClass().getInterfaces(), this);
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Map<String, JMAdvice> advices = this.support.getAdvices(method, this.support.getTargetClass());

        invokeAdvice(advices.get("before"));

        Object returnValue = null;
        try {
            returnValue = method.invoke(this.support.getTarget(), args);
        }catch (Exception e){
            e.printStackTrace();
            invokeAdvice(advices.get("afterThrowing"));
        }


        invokeAdvice(advices.get("after"));

        return returnValue;
    }

    private void invokeAdvice(JMAdvice advice){
        try {
            advice.getAdviceMethod().invoke(advice.getAspect());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}