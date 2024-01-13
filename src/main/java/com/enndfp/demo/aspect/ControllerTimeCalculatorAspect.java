package com.enndfp.demo.aspect;

import com.enndfp.simpleframework.aop.annotation.Aspect;
import com.enndfp.simpleframework.aop.annotation.Order;
import com.enndfp.simpleframework.aop.aspect.DefaultAspect;
import com.enndfp.simpleframework.core.annotation.Controller;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author Enndfp
 */
@Slf4j
@Aspect(value = Controller.class)
@Order(0)
public class ControllerTimeCalculatorAspect extends DefaultAspect {
    private long timestampCache;

    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        log.info("开始计时，执行的类是[{}],执行的方法是[{}]，参数是[{}]",
                targetClass.getName(), method.getName(), args);
        this.timestampCache = System.currentTimeMillis();
    }

    @Override
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) {
        long endTime = System.currentTimeMillis();
        long costTime = endTime - this.timestampCache;
        log.info("结束计时，执行的类是[{}],执行的方法是[{}]，参数是[{}],返回值是[{}]，时间为[{}]ms",
                targetClass.getName(), method.getName(), args, returnValue, costTime);
        return returnValue;
    }
}
