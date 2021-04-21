package com.jinm.spring.framework.beans.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JMBeanDefinitionReader {

    // 全局配置文件
    private Properties contextConfig = new Properties();

    // 配置文件扫描包路径下的所有类的全类名
    private List<String> registerBeanClassFullName = new ArrayList<>();

    public JMBeanDefinitionReader(String...locations) {

        // 1. 加载配置文件
        loadConfig(locations[0]);

        // 2. 扫描相关类
        scanPackage(contextConfig.getProperty("scanPackage"));
        
    }

    private void scanPackage(String scanPackage) {
    }

    private void loadConfig(String location) {
    }
}
