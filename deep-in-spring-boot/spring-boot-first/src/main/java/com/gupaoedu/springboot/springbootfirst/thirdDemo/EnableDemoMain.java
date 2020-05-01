package com.gupaoedu.springboot.springbootfirst.thirdDemo;

import com.gupaoedu.springboot.springbootfirst.firstDemo.ConfigurationMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
@EnableDefineService
public class EnableDemoMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext ca= SpringApplication.run(EnableDemoMain.class,args);

        System.out.println(ca.getBean(CacheService.class));
        System.out.println(ca.getBean(LoggerService.class));
    }


}
