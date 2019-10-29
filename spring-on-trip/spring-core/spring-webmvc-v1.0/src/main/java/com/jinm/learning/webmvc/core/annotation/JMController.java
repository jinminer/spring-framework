package com.jinm.learning.webmvc.core.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author jinm  2019/10/30 00:47.
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JMController {

    String value() default "";

}
