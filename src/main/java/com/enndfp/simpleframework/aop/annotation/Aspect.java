package com.enndfp.simpleframework.aop.annotation;

import java.lang.annotation.*;

/**
 * @author Enndfp
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    // 切入点
    String pointcut();
}
