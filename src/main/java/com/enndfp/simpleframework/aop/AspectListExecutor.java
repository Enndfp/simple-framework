package com.enndfp.simpleframework.aop;

import com.enndfp.simpleframework.aop.aspect.AspectInfo;
import com.enndfp.simpleframework.utils.ValidationUtil;
import lombok.Getter;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * @author Enndfp
 */
public class AspectListExecutor implements MethodInterceptor {
    // 被代理的类
    private Class<?> targetClass;
    // 排好序的Aspect列表
    @Getter
    private List<AspectInfo> sortedAspectInfoList;

    public AspectListExecutor(Class<?> targetClass, List<AspectInfo> aspectInfoList) {
        this.targetClass = targetClass;
        this.sortedAspectInfoList = sortAspectInfoList(aspectInfoList);
    }

    /**
     * 按照order的值进行升序排序，确保order值小的aspect先被织入
     *
     * @param aspectInfoList 切面集合
     * @return 排序好的切面集合
     */
    private List<AspectInfo> sortAspectInfoList(List<AspectInfo> aspectInfoList) {
        aspectInfoList.sort(new Comparator<AspectInfo>() {
            @Override
            public int compare(AspectInfo o1, AspectInfo o2) {
                return o1.getOrderIndex() - o2.getOrderIndex();
            }
        });
        return aspectInfoList;
    }

    /**
     * 横切逻辑
     *
     * @param proxy
     * @param method
     * @param args
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object returnValue = null;
        collectAccurateMatchedAspectList(method);
        if (ValidationUtil.isEmpty(sortedAspectInfoList)) {
            returnValue = methodProxy.invokeSuper(proxy, args);
            return returnValue;
        }
        // 1. 按照order的顺序升序执行完所有Aspect的before方法
        invokeBeforeAdvices(method, args);
        try {
            // 2. 执行被代理类的方法
            returnValue = methodProxy.invokeSuper(proxy, args);
            // 3. 如果被代理方法正常返回，则按照order的顺序降序执行完所有Aspect的afterReturning方法
            returnValue = invokeAfterReturningAdvices(method, args, returnValue);
        } catch (Exception e) {
            // 4. 如果被代理方法抛出异常，则按照order的顺序降序执行完所有Aspect的afterThrowing方法
            invokeAfterThrowingAdvices(method, args, e);
        }
        return returnValue;
    }

    /**
     * 精筛特定的方法
     *
     * @param method
     */
    private void collectAccurateMatchedAspectList(Method method) {
        if (ValidationUtil.isEmpty(sortedAspectInfoList)) return;
        // for-each不支持动态移除元素，改用迭代器
        Iterator<AspectInfo> iterator = sortedAspectInfoList.iterator();
        while (iterator.hasNext()) {
            AspectInfo aspectInfo = iterator.next();
            if (!aspectInfo.getPointcutLocator().accurateMatches(method)) {
                iterator.remove();
            }
        }
    }

    /**
     * 如果被代理方法抛出异常，则按照order的顺序降序执行完所有Aspect的afterThrowing方法
     *
     * @param method
     * @param args
     * @param e
     */
    private void invokeAfterThrowingAdvices(Method method, Object[] args, Exception e) throws Throwable {
        for (int i = sortedAspectInfoList.size() - 1; i >= 0; i--) {
            sortedAspectInfoList.get(i).getAspectObject().afterThrowing(targetClass, method, args, e);
        }
    }

    /**
     * 如果被代理方法正常返回，则按照order的顺序降序执行完所有Aspect的afterReturning方法
     *
     * @param method
     * @param args
     * @param returnValue
     * @return
     */
    private Object invokeAfterReturningAdvices(Method method, Object[] args, Object returnValue) throws Throwable {
        Object result = null;
        for (int i = sortedAspectInfoList.size() - 1; i >= 0; i--) {
            result = sortedAspectInfoList.get(i).getAspectObject().afterReturning(targetClass, method, args, returnValue);
        }
        return result;
    }

    /**
     * 按照order的顺序升序执行完所有Aspect的before方法
     *
     * @param method
     * @param args
     * @throws Throwable
     */
    private void invokeBeforeAdvices(Method method, Object[] args) throws Throwable {
        for (AspectInfo aspectInfo : sortedAspectInfoList) {
            aspectInfo.getAspectObject().before(targetClass, method, args);
        }
    }
}
