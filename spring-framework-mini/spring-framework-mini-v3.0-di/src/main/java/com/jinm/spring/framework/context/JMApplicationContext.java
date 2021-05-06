package com.jinm.spring.framework.context;

import com.jinm.spring.framework.annotation.JMAutowired;
import com.jinm.spring.framework.annotation.JMController;
import com.jinm.spring.framework.annotation.JMService;
import com.jinm.spring.framework.beans.JMBeanWrapper;
import com.jinm.spring.framework.beans.config.JMBeanDefinition;
import com.jinm.spring.framework.beans.support.JMBeanDefinitionReader;
import com.jinm.spring.framework.beans.support.JMDefaultListableBeanFactory;
import com.jinm.spring.framework.core.JMBeanFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JMApplicationContext implements JMBeanFactory {

    private JMDefaultListableBeanFactory registry = new JMDefaultListableBeanFactory();

    // 三级缓存：终极缓存
    private Map<String, JMBeanWrapper> factoryBeanInstanceCache = new HashMap<>();

    private Map<String, Object> factoryBeanObjectCache = new HashMap<>();

    private JMBeanDefinitionReader reader;

    public JMApplicationContext(String...configLocations) {

        //  1、加载配置文件
        reader = new JMBeanDefinitionReader(configLocations);

        // 2、将 配置文件包路径下的类信息 封装到 BeanDefinition中
        List<JMBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();

        // 3、缓存类的配置信息
        try {
            this.registry.doRegisterBeanDefination(beanDefinitions);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //4、加载非延时加载的所有的Bean
        doLoadInstance();

    }

    private void doLoadInstance() {

        // 循环调用 getBean 方法
        for (Map.Entry<String, JMBeanDefinition> entry : registry.beanDefinitionMap.entrySet()){
            if (entry.getValue().isLazyInit()){
                continue;
            }
            getBean(entry.getKey());
        }

    }
    @Override
    public Object getBean(Class<?> beanClass) {
        return getBean(beanClass.getName());
    }


    @Override
    public Object getBean(String beanName) {

        if (factoryBeanObjectCache.get(beanName) != null){
            return factoryBeanObjectCache.get(beanName);
        }

        //1、先拿到BeanDefinition配置信息
        JMBeanDefinition beanDefinition = registry.beanDefinitionMap.get(beanName);

        //2、反射实例化对象
        Object instance = instantiateBean(beanName,beanDefinition);

        //3、将返回的Bean的对象封装成BeanWrapper
        JMBeanWrapper beanWrapper = new JMBeanWrapper(instance);

        //4、执行依赖注入
        populateBean(beanName,beanDefinition,beanWrapper);

        //5、保存到IoC容器中
        this.factoryBeanInstanceCache.put(beanName,beanWrapper);

        return beanWrapper.getWrappedInstance();
    }

    private Object instantiateBean(String beanName, JMBeanDefinition beanDefinition) {
        String className = beanDefinition.getBeanClassName();
        Object instance = null;
        try {
            Class<?> clazz = Class.forName(className);

            instance = clazz.newInstance();

            //如果是代理对象,触发AOP的逻辑

            this.factoryBeanObjectCache.put(beanName,instance);
        }catch (Exception e){
            e.printStackTrace();
        }
        return instance;
    }
    private void populateBean(String beanName, JMBeanDefinition beanDefinition, JMBeanWrapper beanWrapper) {

        Object instance = beanWrapper.getWrappedInstance();

        Class<?> clazz = beanWrapper.getWrappedClass();

        if(!(clazz.isAnnotationPresent(JMController.class) || clazz.isAnnotationPresent(JMService.class))){
            return;
        }

        //忽略字段的修饰符，不管你是 private / protected / public / default
        for (Field field : clazz.getDeclaredFields()) {
            if(!field.isAnnotationPresent(JMAutowired.class)){ continue; }

            JMAutowired autowired = field.getAnnotation(JMAutowired.class);
            String autowiredBeanName = autowired.value().trim();
            if("".equals(autowiredBeanName)){
                autowiredBeanName = field.getType().getName();
            }

            //代码在反射面前，那就是裸奔
            //强制访问，强吻
            field.setAccessible(true);

            try {
//                if(this.factoryBeanInstanceCache.get(autowiredBeanName) == null){
//                    continue;
//                }
//
//                //相当于 demoAction.demoService = ioc.get("com.gupaoedu.demo.service.IDemoService");
//                field.set(instance,this.factoryBeanInstanceCache.get(autowiredBeanName).getWrappedInstance());
                field.set(instance, getBean(autowiredBeanName));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }

    public int getBeanDefinitionCount(){
        return this.registry.beanDefinitionMap.size();
    }

    public String[] getBeanDefinitionNames(){
        return this.registry.beanDefinitionMap.keySet().toArray(new String[0]);
    }

}
