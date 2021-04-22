package com.jinm.spring.framework.core;

public interface JMBeanFactory {

    public Object getBean(String beanName);

    public Object getBean(Class<?> beanClass);

}
