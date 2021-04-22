package com.jinm.spring.framework.beans.support;

import com.jinm.spring.framework.beans.config.JMBeanDefinition;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public List<JMBeanDefinition> loadBeanDefinitions(){

        List<JMBeanDefinition> beanDefinitions = new ArrayList<>();
        try {
            for (String className : this.registerBeanClassFullName) {
                Class<?> beanClass = Class.forName(className);

                // 如果是接口不处理
                if (beanClass.isInterface()){
                    continue;
                }

                // 1.默认类名首字母小写的情况

                beanDefinitions.add(doCreateBeanDefinition(toLowerFirstCase(beanClass.getSimpleName()), beanClass.getName()));

                // 3. 如果是接口，实例化它的实现类
                for (Class<?> i : beanClass.getInterfaces()) {

                    beanDefinitions.add(doCreateBeanDefinition(i.getName(), beanClass.getName()));
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return beanDefinitions;
    }

    private JMBeanDefinition doCreateBeanDefinition(String factoryBeanName, String factoryBeanClassName) {
        JMBeanDefinition beanDefinition = new JMBeanDefinition();
        beanDefinition.setFactoryBeanName(factoryBeanName);
        beanDefinition.setBeanClassName(factoryBeanClassName);
        return beanDefinition;
    }

    private void scanPackage(String scanPackage) {

        URL rootUrl = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.","/"));
        File classPath = new File(rootUrl.getFile());

        for (File file : Objects.requireNonNull(classPath.listFiles())){
            if (file.isDirectory()){
                scanPackage(scanPackage +"." + file.getName());
                continue;
            }

            if (!file.getName().endsWith(".class")){
                continue;
            }

            String className = (scanPackage + "." + file.getName().replace(".class", ""));
            registerBeanClassFullName.add(className);

        }

    }

    private void loadConfig(String location) {
        URL propertiesPath = this.getClass().getClassLoader().getResource(location.replaceAll("classpath:", ""));
        try(FileInputStream inputStream = new FileInputStream(propertiesPath.getFile())){
            this.contextConfig.load(inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String toLowerFirstCase(String simpleName) {
        char [] chars = simpleName.toCharArray();
        chars[0] += 32;     //利用了ASCII码，大写字母和小写相差32这个规律
        return String.valueOf(chars);
    }
}
