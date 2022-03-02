package com.jinm.learning.webmvc.servlet;

import com.jinm.learning.aop.proxy.JMDefaultAopProxyFactory;
import com.jinm.learning.aop.config.JMAopConfig;
import com.jinm.learning.aop.support.JMAdvisedSupport;
import com.jinm.learning.webmvc.core.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
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

    // 代理工厂
    JMDefaultAopProxyFactory proxyFactory = new JMDefaultAopProxyFactory();

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
        * */
        loadConfig(config.getInitParameter("contextConfigLocation"));

        /*
         * 2.扫描相关类：
         *      根据 key 获取 Properties 对象键值对内容 "scan.package=com.jinm.learning.webmvc" 中的 value 值，即包名："com.jinm.learning.webmvc" ；
         *      这里是以 Properties 作为配置文件，如果使用 xml 文件，读取配置较为复杂；
         *      扫描该包路径，得到的包路径下的相关类的类名；
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
                scanPackage(scanPackage  + "." +  file.getName());
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

                    //  2.类名首字母小写
                    if ("".equals(beanName)){
                        beanName = toLowerCaseFirst(clazz.getSimpleName());
                    }

                    Object instance = clazz.newInstance();

                    // 判断规则是否要生成代理类，如果是，则调用代理工厂生成代理类，并且放入到三级缓存
                    // 如果不符合，返回原生类
                    JMAdvisedSupport support = instantionAopConfig();
                    support.setTargetClass(clazz);
                    support.setTarget(instance);


                    if (support.pointCutMatch()){
                        instance = proxyFactory.createAopProxy(support).getProxy();
                    }

                    // ioc 容器存储
                    // 类初始化
                    iocContainer.put(beanName, instance);

                    // 3.根据类型自动赋值
                    for (Class<?> i : clazz.getInterfaces()){

                        if (iocContainer.containsKey(i.getName())){
                            throw new Exception("The '" + i.getName() + "' exists" );
                        }
                        iocContainer.put(i.getName(), instance);

                    }

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
                if ("".equals(beanName)){
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

        /*
        * 获取 request 请求中的参数值，并与 Controller 中的具体方法进行映射
        * */
        Object[] parameters = requestParametersMapping(req, resp, method);

        //反射调用 Controller 类的具体方法
//        method.invoke(iocContainer.get(beanName), req, resp, params.get("name")[0]);
        method.invoke(iocContainer.get(beanName), parameters);

    }

    private Object[] requestParametersMapping(HttpServletRequest req, HttpServletResponse resp, Method method) {

        /*
        * request 请求中的参数：
        *   这里 map 中的 value 定义为 String[] 数组类型 ，
        *   是因为在实际的请求中，一个参数可能对应多个值，即一个 key 对应多个 value ，如 ：
        *   http://127.0.0.1:8080/jinm/test?name=jinm&name=godfather
        * */
        Map<String, String[]> requestParams = req.getParameterMap();

        // Controller 类中，具体处理请求方法的 形参列表
        Class<?>[] methodParameterTypes = method.getParameterTypes();

        // 赋值给 Controller 类中，具体处理请求方法的 实参
        Object[] paramValues = new Object[methodParameterTypes.length];

        for (int i = 0; i < methodParameterTypes.length; i++) {

            if (methodParameterTypes[i] == HttpServletRequest.class){

                paramValues[i] = req;
                continue;

            }

            if (methodParameterTypes[i] == HttpServletResponse.class){

                paramValues[i] = resp;
                continue;

            }

            /*
            * 获取 Controller 类中，具体处理请求方法的 形参上的注解：
            *   返回值为 二维数组：
            *       横向维度：每个添加了注解的形参，在方法中的位置/坐标
            *       纵向维度：每个形参上的具体注解（一个参数可能被多个注解修饰）
            * */
            Annotation[][] methodParameterAnnotations = method.getParameterAnnotations();

            if (methodParameterTypes[i] == String.class){

                for (int j = 0; j < methodParameterAnnotations.length; j++) {
                    for (Annotation annotation : methodParameterAnnotations[i]){
                        if (annotation instanceof JMRequestParam){
                            String paramName = ((JMRequestParam) annotation).value();
                            if (!"".equals(paramName)){
                                String value = Arrays.toString(requestParams.get(paramName))
                                        // 去除数据元素两边的 [] 符号
                                        .replaceAll("\\[|\\]","")
                                        .replaceAll("\\s",",");
                                paramValues[i] = value;
                            }
                        }
                    }
                }

            }

        }

        return paramValues;
    }

    private JMAdvisedSupport instantionAopConfig(){

        JMAopConfig aopConfig = new JMAopConfig();

        aopConfig.setPointCut(contextConfig.getProperty("pointCut"));
        aopConfig.setAspectClass(contextConfig.getProperty("aspectClass"));
        aopConfig.setAspectBefore(contextConfig.getProperty("aspectBefore"));
        aopConfig.setAspectAfter(contextConfig.getProperty("aspectAfter"));
        aopConfig.setAspectAfterThrow(contextConfig.getProperty("aspectAfterThrow"));
        aopConfig.setAspectAfterThrowingName(contextConfig.getProperty("aspectAfterThrowingName"));

        return new JMAdvisedSupport(aopConfig);

    }

    private String toLowerCaseFirst(String name) {
        char[] chars = name.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

}
