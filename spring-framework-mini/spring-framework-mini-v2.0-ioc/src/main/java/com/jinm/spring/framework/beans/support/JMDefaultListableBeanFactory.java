package com.jinm.spring.framework.beans.support;

import com.jinm.spring.framework.beans.config.JMBeanDefinition;
import com.jinm.spring.framework.core.JMBeanFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JMDefaultListableBeanFactory implements JMBeanFactory {

    Map<String, JMBeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public Object getBean(String beanName) {
        return null;
    }

    @Override
    public Object getBean(Class<?> beanClass) {
        return null;
    }

    public void doRegisterBeanDefination(List<JMBeanDefinition> beanDefinitions) throws Exception {
        for (JMBeanDefinition beanDefinition : beanDefinitions){
            if (beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())){
                throw new Exception("The " + beanDefinition.getFactoryBeanName() + " is exists!!!");
            }
            this.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }
    }

}
