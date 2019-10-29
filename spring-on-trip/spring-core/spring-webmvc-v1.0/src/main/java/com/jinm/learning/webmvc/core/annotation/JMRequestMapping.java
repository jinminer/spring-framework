package com.jinm.learning.webmvc.core.annotation;

import java.lang.annotation.*;

/**
 * @author jinm 2019/10/30 00:57.
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JMRequestMapping {

    String value() default "";

}
