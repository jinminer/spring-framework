package com.jinm.learning.aspect;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author jinmin
 */
@Data
public class JMAdvice {

    private Object aspect;
    private Method adviceMethod;
    private String throwName;


    public JMAdvice(Object aspect, Method adviceMethod) {
        this.aspect = aspect;
        this.adviceMethod = adviceMethod;
    }
}
