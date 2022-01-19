package com.jinm.learning.aop.proxy;

import com.jinm.learning.aop.support.JMAdvisedSupport;

public class JMDefaultAopProxyFactory {


    public JMAopProxy createAopProxy(JMAdvisedSupport support) {

        if (support.getTargetClass().getInterfaces().length > 0){
            return new JMJdkDynamicAopProxy(support);
        }

        return new JMCglibAopProxy(support);
    }
}
