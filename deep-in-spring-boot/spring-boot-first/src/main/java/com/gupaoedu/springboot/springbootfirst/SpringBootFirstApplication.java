package com.gupaoedu.springboot.springbootfirst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.support.SpringFactoriesLoader;

@SpringBootApplication(exclude = {AopAutoConfiguration.class})
public class SpringBootFirstApplication {

    //启动的逻辑不分析
    public static void main(String[] args) {
        /*ConfigurableApplicationContext ca=
                SpringApplication.run(SpringBootFirstApplication.class, args);

        String[] beanDefs=ca.getBeanDefinitionNames();
        for(String str:beanDefs){
            System.out.println(str);
        }*/

    }

}
