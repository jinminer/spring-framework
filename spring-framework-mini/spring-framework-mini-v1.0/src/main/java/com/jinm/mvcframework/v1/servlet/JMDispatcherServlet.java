package com.jinm.mvcframework.v1.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class JMDispatcherServlet extends HttpServlet {

    //保存用户配置好的配置文件
    private Properties contextConfig = new Properties();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        // 1. 加载配置文件
        loadConfiguration(config);

        // 2. 扫描相关类
        doScanner(contextConfig.getProperty("scanPackage"));

        // 3. 初始化 ioc 容器

        // 4. 实例化相关类，并缓存到 ioc 容器

        // 5. 根据类的定义信息，将 ioc 中的类实例，自动赋值到类的字段，di

        // 6. 将request url 和 request method 映射为 handlermapping

        // 7. 运行

    }

    private void doScanner(String scanPackage) {
    }

    private void loadConfiguration(ServletConfig config) {
        URL propertiesPath = this.getClass().getClassLoader().getResource(config.getInitParameter("contextConfigLocation"));
        try(FileInputStream inputStream = new FileInputStream(propertiesPath.getFile())){
            this.contextConfig.load(inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }

        this.contextConfig.getProperty("scanPackage");

    }
}
