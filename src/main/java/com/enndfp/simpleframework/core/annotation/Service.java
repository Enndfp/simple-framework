package com.enndfp.simpleframework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Enndfp
 */
@Target(ElementType.TYPE) // 作用于类
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
public @interface Service {
}
