package com.jinm.spring.v1.servlet;

import com.jinm.spring.v1.annotation.JMController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@JMController("")
public class JmDispatherServlet extends HttpServlet {

    private final Properties contextConfig = new Properties();

    private final List<String> classNames = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 请求转发
        doDispatch(req, resp);

    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {

    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        // 1. 加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));

        // 2. 扫描配置文件中的包路径下的相关类
        doScanner(contextConfig.getProperty("scanPackage"));

        // 3. 初始化扫描到的类，并将其保持到 IOC 容器中
        doInstance();

        // 4. 依赖注入
        doAutowired();

        // 5. 初始化 handler mapping
        initHandlerMapping();

        System.out.println("jinm spring-mvc like system init succeeded ......");

    }

    private void doLoadConfig(String contextConfigLocation) {

        try(InputStream fis = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);) {
            contextConfig.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void doScanner(String scanPackage) {

        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classPath = new File(url.getFile());

        for (File file : classPath.listFiles()){
            if (file.isDirectory()){
                doScanner(scanPackage + file.getName());
            }else {
                String className = file.getName();
                if (!className.endsWith(".class")){
                    continue;
                }
                classNames.add(className.replace(".class", ""));
            }
        }

    }

    private void doInstance() {

    }

    private void doAutowired() {

    }

    private void initHandlerMapping() {

    }
}
