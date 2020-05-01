package com.gupaoedu.springboot.springbootfirst.fouthDemo;

import com.gupaoedu.core.GupaoCore;
import com.gupaoedu.springboot.springbootfirst.thirdDemo.CacheService;
import com.gupaoedu.springboot.springbootfirst.thirdDemo.EnableDemoMain;
import com.gupaoedu.springboot.springbootfirst.thirdDemo.LoggerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class FouthMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext ca= SpringApplication.run(EnableDemoMain.class,args);

        System.out.println(ca.getBean(GupaoCore.class).study());
    }
}
