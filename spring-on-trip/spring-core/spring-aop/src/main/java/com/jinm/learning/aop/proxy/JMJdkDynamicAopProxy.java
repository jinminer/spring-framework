package com.jinm.learning.aop.proxy;

import com.jinm.learning.aop.support.JMAdvisedSupport;

public class JMJdkDynamicAopProxy implements JMAopProxy{

    private JMAdvisedSupport support;

    public JMJdkDynamicAopProxy(JMAdvisedSupport support) {

        this.support = support;
    }

    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}