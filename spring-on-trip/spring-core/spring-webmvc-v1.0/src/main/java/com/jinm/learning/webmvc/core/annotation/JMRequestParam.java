package com.jinm.learning.webmvc.core.annotation;

import java.lang.annotation.*;

/**
 * @author jinm 2019/11/12 22:56.
 */

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JMRequestParam {

    String value() default "";

}
