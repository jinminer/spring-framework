package com.jinm.learning.webmvc.servlet;

import com.jinm.learning.webmvc.core.annotation.JMAutowired;
import com.jinm.learning.webmvc.core.annotation.JMController;
import com.jinm.learning.webmvc.core.annotation.JMRequestMapping;
import com.jinm.learning.webmvc.core.annotation.JMService;
import org.springframework.util.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * jinm dispatcher servlet
 * @author jinm 2019/10/23 00:26.
 */

public class JmDispatherServlet extends HttpServlet {

    // 保存配置文件 application.properties 中的内容(内存中)
    private Properties contextConfig = new Properties();

    // 保存类名全路径
    private List<String> classNameList = new ArrayList<String>();

    // IOC 容器
    Map<String, Object> iocContainer = new HashMap<String, Object>();

    // 保存 method 和 url 的对应关系
    Map<String, Method> handlerMapping = new HashMap<String, Method>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*
        * 6.调用：
        *       运行阶段
        * */
        try {
            dispatch(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("500 Exception, Detail : " + Arrays.toString(e.getStackTrace()));
            return;
        }

    }



    /** 初始化阶段*/
    @Override
    public void init(ServletConfig config) throws ServletException {

        /*
        * 1.加载配置文件：
        *       读取配置："scan.package=com.jinm.learning.webmvc"  - 类扫描的包路径；
        *       这里由于.xml文件(applicationContext.xml)读取比较麻烦，所以使用读取较为容易的application.properties；
        * */
        loadConfig(config.getInitParameter("contextConfigLocation"));

        /*
         * 2.扫描相关类：
         *      根据 key 获取 Properties 对象键值对内容 "scan.package=com.jinm.learning.webmvc" 中的 value 值，即包名："com.jinm.learning.webmvc" ；
         *      这里是以 Properties 作为配置文件，如果使用 xml 文件，读取配置较为复杂；
         *      扫描该包路径，得到的包路径下的相关类；
         *
         * */
        scanPackage(contextConfig.getProperty("scan.package"));

        /*
        * 3.IOC容器实现：
        *       初始化扫描到的类，并将实例化对象存储到容器中；
        * */
        initContainer();

        /*
        * 4.DI依赖注入：
        *       动态加载类实例；
        * */
        autoWired();

        /*
        * 5.初始化 HandlerMapping
        * */
        initHandlerMapping();

        System.out.println("jinm spring web mvc started... ");

    }

    // 加载配置文件
    private void loadConfig(String contextConfigLocation) {

        /* 从类路径下找到 spring 主配置文件所在的路径；
        *   并将其以流的形式读取出来；
        *   再将其存储到 Properties 对象中：
        *       即把配置文件中的内容 "scan.package=com.jinm.learning.webmvc" ，从文件中转移到内存
        * */
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);

        try {
            contextConfig.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    //扫描相关类
    private void scanPackage(String scanPackage) {

        /*
        *   扫描配置文件中包名 "com.jinm.learning.webmvc"  对应类路径下的相关类；
        *   将包名转换为文件路径，得到完整的类路径即：classpath；
        * */
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));

        /*
        *   将文件路径转换为文件对象，在循环遍历读取各个类文件
        * */
        File classPath = new File(url.getFile());

        /* 迭代 classpath 路径下的所有文件*/
        for (File file : classPath.listFiles()){

            // 如果 file 对象是一个目录，则继续递归扫描
            if (file.isDirectory()){
                scanPackage(scanPackage + file.getName());
                continue;
            }

            // 如果 file 对象是文件，并且是 class 文件，则将对应的类名(文件名) 全路径进行存储
            if (file.getName().endsWith(".class")){
                String className = scanPackage + "." + file.getName().replace(".class", "");
                classNameList.add(className);
            }

        }

    }

    // 初始化相关类，并将其保存到 IOC 容器中，为后续的 DI 做准备
    private void initContainer() {

        if (classNameList.isEmpty()){
            return;
        }

        try {
            for (String className : classNameList){

                /*
                *  什么样的类才需要进行初始化操作？
                *       加了注解的类：因为 DI 操作就是通过注解(bean的xml配置)进行动态加载的
                *  接口类如何进行初始化操作？
                *       接口类初始化其对应的实现类
                *
                * */
                Class<?> clazz = Class.forName(className);

                String beanName = null;

                // 对加了注解的类进行初始化操作
                if (clazz.isAnnotationPresent(JMController.class)){

                    //  类名首字母小写
                    beanName = toLowerCaseFirst(clazz.getSimpleName());
                    iocContainer.put(beanName, clazz.newInstance());

                }else if (clazz.isAnnotationPresent(JMService.class)){


                    // 1.自定义bean name，即在注解中传参的形式
                    JMService service = clazz.getAnnotation(JMService.class);
                    beanName = service.value();

                    //  3.类名首字母小写
                    if (StringUtils.isEmpty(beanName)){
                        beanName = toLowerCaseFirst(clazz.getSimpleName());
                    }

                    // ioc 容器存储
                    // 类初始化
                    iocContainer.put(beanName, clazz.newInstance());

                    // 2.根据类型自动赋值
                    for (Class<?> i : clazz.getInterfaces()){

                        if (iocContainer.containsKey(i.getName())){
                            throw new Exception("The '" + i.getName() + "' exists" );
                        }
                        iocContainer.put(i.getName(), clazz.newInstance());

                    }


                }else {
                    continue;
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // DI：依赖注入
    private void autoWired() {

        if (iocContainer.isEmpty()){
            return;
        }

        for (Map.Entry<String, Object> entry : iocContainer.entrySet()){

            // Declare：类中声明的所有字段，包括 public、protect、private、default
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields){
                if (!field.isAnnotationPresent(JMAutowired.class)){
                    continue;
                }

                JMAutowired autowired = field.getAnnotation(JMAutowired.class);
                String beanName = autowired.value();

                // 如果没有自定义 beanName，默认使用类型注入
                if (StringUtils.isEmpty(beanName)){
                    beanName = field.getType().getName();
                }

                // 暴力访问：如果类属性的访问限制高于 public，也强制赋值
                field.setAccessible(true);
                try {
                    // 反射动态给类的属性赋值
                    field.set(entry.getValue(), iocContainer.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    // 初始化 method 和 url 一一对应关系
    private void initHandlerMapping() {

        if (iocContainer.isEmpty()){
            return;
        }

        for (Map.Entry<String, Object> entry : iocContainer.entrySet()){

            Class<?> clazz = entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(JMController.class)){
                continue;
            }

            // 类的 url
            String baseUrl = "";
            if (clazz.isAnnotationPresent(JMRequestMapping.class)){
                JMRequestMapping requestMapping = clazz.getAnnotation(JMRequestMapping.class);
                baseUrl = requestMapping.value();
            }

            // 方法的 url
            // 获取 public 方法
            for (Method method : clazz.getMethods()){

                if (!method.isAnnotationPresent(JMRequestMapping.class)){
                    continue;
                }

                String realUrl = (baseUrl + "/" + method.getAnnotation(JMRequestMapping.class).value()).replaceAll("/+", "/");
                handlerMapping.put(realUrl, method);
                System.out.println("Mapped:" + realUrl + "--> method:" + method);

            }

        }

    }

    private void dispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception{

        // 客户端请求访问的 url：绝对路径
        String url = req.getRequestURI();

        // 将客户端请求的绝对路径，转换为工程中的相对路径
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath, "").replaceAll("/+", "/");

        if (!handlerMapping.containsKey(url)){
            resp.getWriter().write("404 Not Found !!!!");
            return;
        }

        Method method = handlerMapping.get(url);

        String beanName = toLowerCaseFirst(method.getDeclaringClass().getSimpleName());

        Map<String, String[]> params = req.getParameterMap();

        method.invoke(iocContainer.get(beanName), new Object[]{req, resp, params.get("name")[0]});

    }

    private String toLowerCaseFirst(String name) {
        char[] chars = name.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

}
