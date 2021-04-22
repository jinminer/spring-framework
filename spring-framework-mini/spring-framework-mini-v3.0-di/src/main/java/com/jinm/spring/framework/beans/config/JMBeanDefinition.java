package com.jinm.spring.framework.beans.config;

public class JMBeanDefinition {

    // 实例 bean 的名称
    private String factoryBeanName;

    // bean 原生类的全类名
    private String beanClassName;

    public boolean isLazyInit(){
        return false;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }
}
