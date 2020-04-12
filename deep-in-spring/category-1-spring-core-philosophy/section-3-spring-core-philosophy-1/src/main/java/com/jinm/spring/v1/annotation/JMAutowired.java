package com.jinm.spring.v1.annotation;

import java.lang.annotation.*;

/**
 * @author jinm 2019/11/05 23:22.
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JMAutowired {

    String value() default "";

}
