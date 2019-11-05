package com.jinm.learning.webmvc.core.annotation;

import java.lang.annotation.*;

/**
 * @author jinm 2019/11/05 23:22.
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JMAutowired {

    String value() default "";

}
