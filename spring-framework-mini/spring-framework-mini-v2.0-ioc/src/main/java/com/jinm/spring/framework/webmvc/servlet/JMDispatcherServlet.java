package com.jinm.spring.framework.webmvc.servlet;

import com.jinm.spring.framework.annotation.*;
import com.jinm.spring.framework.context.JMApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class JMDispatcherServlet extends HttpServlet {

    //IoC容器的访问上下文
    private JMApplicationContext applicationContext = null;

    //保存Controller中URL和Method的对应关系
    private Map<String,Method> handlerMapping = new HashMap<String, Method>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String requestUrl = req.getRequestURI().replaceAll(req.getContextPath(), "").replaceAll("/+", "/");
        if(handlerMapping.isEmpty() || !this.handlerMapping.containsKey(requestUrl)){
            resp.getWriter().write("404 Not Found!!!");
            return;
        }

        Method requestMethod = handlerMapping.get(requestUrl);

        // 参数位置和名称进行映射
        Map<String, Integer> paramIndexMapping = new HashMap<>();

        // 方法参数的注解：一个参数可能有多个注解
        Annotation[][] paramAnotations = requestMethod.getParameterAnnotations();
        for (int i = 0; i < paramAnotations.length; i++){  // 遍历每个参数
            for (Annotation annotation : paramAnotations[i]){  // 遍历参数上的每个注解
                if (annotation instanceof JMRequestParam){
                    String paramName = ((JMRequestParam) annotation).value();
                    if (!"".equals(paramName)){
                        paramIndexMapping.put(paramName, i);
                    }
                }
            }
        }

        Class<?> [] paramTypes = requestMethod.getParameterTypes();
        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> type = paramTypes[i];
            if(type == HttpServletRequest.class || type == HttpServletResponse.class){
                paramIndexMapping.put(type.getName(),i);
            }
        }

        //2、根据参数位置匹配参数名字，从url中取到参数名字对应的值
        Object[] paramValues = new Object[paramTypes.length];

        //http://localhost/demo/query?name=Tom&name=Tomcat&name=Mic
        Map<String,String[]> params = req.getParameterMap();
        for (Map.Entry<String, String[]> param : params.entrySet()) {
            String value = Arrays.toString(param.getValue())
                    .replaceAll("\\[|\\]","")
                    .replaceAll("\\s","");

            if(!paramIndexMapping.containsKey(param.getKey())){continue;}

            int index = paramIndexMapping.get(param.getKey());

            //涉及到类型强制转换
            paramValues[index] = value;
        }

        if(paramIndexMapping.containsKey(HttpServletRequest.class.getName())){
            int index = paramIndexMapping.get(HttpServletRequest.class.getName());
            paramValues[index] = req;
        }

        if(paramIndexMapping.containsKey(HttpServletResponse.class.getName())){
            int index = paramIndexMapping.get(HttpServletResponse.class.getName());
            paramValues[index] = resp;
        }

        String beanName = toLowerFirstCase(requestMethod.getDeclaringClass().getSimpleName());
        //3、组成动态实际参数列表，传给反射调用
        try {
            requestMethod.invoke(applicationContext.getBean(beanName),paramValues);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void init(ServletConfig config) throws ServletException {

            applicationContext = new JMApplicationContext(config.getInitParameter("contextConfigLocation"));
        // 1. 加载配置文件
//        loadConfiguration(config);

        // 2. 扫描相关类
//        doScanner(contextConfig.getProperty("scanPackage"));

        // 3. 初始化 ioc 容器，实例化相关类，并缓存到 ioc 容器
//        doInstance();

        // 4. 根据类的定义信息，将 ioc 中的类实例，自动赋值到类的字段，完成依赖注入di
//        doAutowired();

        // 5. 将request url 和 request method 映射为 handlermapping
        doInitHandlerMapping();

        System.out.println("GP Spring framework is init.");

    }


    private void doInitHandlerMapping() {

        if (applicationContext.getBeanDefinitionCount() < 1){
            return;
        }

        for (String beanName : applicationContext.getBeanDefinitionNames()){

            Object bean = applicationContext.getBean(beanName);
            Class<?> clazz = bean.getClass();
            // 非 Controller 类忽略
            if (!clazz.isAnnotationPresent(JMController.class)){
                continue;
            }

            String baseUrl = "";

            // Controller 类的 url
            if (clazz.isAnnotationPresent(JMRequestMapping.class)){
                baseUrl = clazz.getAnnotation(JMRequestMapping.class).value();
            }

            // 方法的 url
            for (Method method : clazz.getMethods()){
                if (!method.isAnnotationPresent(JMRequestMapping.class)){
                    continue;
                }
                String requestMapping = ("/" + baseUrl + "/" + method.getAnnotation(JMRequestMapping.class).value()).replaceAll("/+","/");

                // 访问方法的 url 和 方法 进行 映射
                handlerMapping.put(requestMapping, method);
                System.out.println("Mapped : " + requestMapping + " --> " + method);
            }

        }


    }

    private String toLowerFirstCase(String simpleName) {
        char [] chars = simpleName.toCharArray();
        chars[0] += 32;     //利用了ASCII码，大写字母和小写相差32这个规律
        return String.valueOf(chars);
    }
}
