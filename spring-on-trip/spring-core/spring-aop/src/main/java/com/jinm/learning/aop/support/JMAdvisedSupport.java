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

    private Map<Method, Map<String, JMAdvice>> methodCache;

    public JMAdvisedSupport(JMAopConfig aopConfig) {
        this.aopConfig = aopConfig;
    }

    //解析配置文件的方法
    private void parse() {

        // public .* com.jinmin.learning.webmvc.service..*Service..*(.*)
        //把Spring的Excpress变成Java能够识别的正则表达式
        String pointCut = aopConfig.getPointCut()
                .replaceAll("\\.", "\\\\.")
                .replaceAll("\\\\.\\*", ".*")
                .replaceAll("\\(", "\\\\(")
                .replaceAll("\\)", "\\\\)");


        //保存专门匹配Class的正则
        // public .* com.jinmin.learning.webmvc.service..*Service
        String pointCutForClassRegex = pointCut.substring(0, pointCut.lastIndexOf("\\(") - 4);

        // com.jinmin.learning.webmvc.service..*Service
        pointCutClassPattern = Pattern.compile(pointCutForClassRegex.substring(pointCutForClassRegex.lastIndexOf(" ") + 1));


        // 保存回调通知和目标切点方法之间的关系
        // query before() after()
        // add before() after() afterThrowing() rollback




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
}
