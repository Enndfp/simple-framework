package com.enndfp.simpleframework.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求的方法参数名称
 *
 * @author Enndfp
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {
    // 方法参数名称
    String value() default "";

    // 该参数是否必须
    boolean required() default true;
}
