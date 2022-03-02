package com.jinm.learning.aop.support;

import com.jinm.learning.aop.config.JMAopConfig;
import com.jinm.learning.aspect.JMAdvice;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JMAdvisedSupport {

    private JMAopConfig aopConfig;

    private Class targetClass;

    private Object target;

    private Pattern pointCutClassPattern;

    private Map<Method, Map<String, JMAdvice>> methodCache = new HashMap<>();

    public JMAdvisedSupport(JMAopConfig aopConfig) {
        this.aopConfig = aopConfig;
    }

    //解析配置文件的方法
    private void parse() {

        // public .* com.jinmin.learning.webmvc.service..*Service..*(.*)
        //把Spring的Excpress变成Java能够识别的正则表达式
        String pointCutRegx = aopConfig.getPointCut()
                .replaceAll("\\.", "\\\\.")
                .replaceAll("\\\\.\\*", ".*")
                .replaceAll("\\(", "\\\\(")
                .replaceAll("\\)", "\\\\)");


        //保存专门匹配Class的正则
        // public .* com.jinmin.learning.webmvc.service..*Service
        String pointCutForClassRegex = pointCutRegx.substring(0, pointCutRegx.lastIndexOf("\\(") - 4).replaceAll("\\\\.", ".");

        // com.jinmin.learning.webmvc.service..*Service
        pointCutClassPattern = Pattern.compile(pointCutForClassRegex.substring(pointCutForClassRegex.lastIndexOf(" ") + 1));


        // 保存回调通知和目标切点方法之间的关系
        // query before() after()
        // add before() after() afterThrowing() rollback

        // 先把切面方法缓存起来，方便解析 AOP配置文件的时候，可以根据方法名快速找到对于的回调方法
        Map<String, Method> aspectMethods = new HashMap<String, Method>();
        try {
            Class aspectClass = Class.forName(this.aopConfig.getAspectClass());

            for (Method method : aspectClass.getMethods()){
                aspectMethods.put(method.getName(), method);
            }

            Pattern pointCutPattern = Pattern.compile(pointCutRegx);

            for (Method method : this.targetClass.getMethods()){

                // public java.lang.String com.jinm.learning.webmvc.service.impl.TestServiceImpl.testService(java.lang.String)
                String methodString = method.toString();

                if (methodString.contains("throws")){
                    methodString = methodString.substring(0, methodString.lastIndexOf("throws")).trim();
                }

                Matcher matcher = pointCutPattern.matcher(methodString);

                if (matcher.matches()){
                    Map<String, JMAdvice> advices = new HashMap<String, JMAdvice>();

                    if (!(null == this.aopConfig.getAspectBefore() || "".equals(this.aopConfig.getAspectBefore()))){
                        advices.put("before", new JMAdvice(aspectClass.newInstance(), aspectMethods.get(this.aopConfig.getAspectBefore())));
                    }
                    if (!(null == this.aopConfig.getAspectAfter() || "".equals(this.aopConfig.getAspectAfter()))){
                        advices.put("after", new JMAdvice(aspectClass.newInstance(), aspectMethods.get(this.aopConfig.getAspectAfter())));
                    }
                    if (!(null == this.aopConfig.getAspectAfterThrow() || "".equals(this.aopConfig.getAspectAfterThrow()))){

                        JMAdvice advice = new JMAdvice(aspectClass.newInstance(), aspectMethods.get(this.aopConfig.getAspectAfterThrow()));
                        advice.setThrowName(this.aopConfig.getAspectAfterThrowingName());

                        advices.put("afterThrowing", advice);
                    }

                    this.methodCache.put(method, advices);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public JMAopConfig getAopConfig() {
        return aopConfig;
    }

    public void setAopConfig(JMAopConfig aopConfig) {
        this.aopConfig = aopConfig;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
        parse();
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public boolean pointCutMatch() {
        return this.pointCutClassPattern.matcher(this.targetClass.getName()).matches();
    }

    public Map<String, JMAdvice> getAdvices(Method method, Class targetClass) throws NoSuchMethodException {

        Map<String, JMAdvice> cache = this.methodCache.get(method);
        if (null == cache){
            Method m = targetClass.getMethod(method.getName(), method.getParameterTypes());
            cache = methodCache.get(m);
            this.methodCache.put(m, cache);
        }

        return cache;
    }
}
