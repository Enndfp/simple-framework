package com.enndfp.simpleframework.aop;

import com.enndfp.simpleframework.aop.annotation.Aspect;
import com.enndfp.simpleframework.aop.annotation.Order;
import com.enndfp.simpleframework.aop.aspect.AspectInfo;
import com.enndfp.simpleframework.aop.aspect.DefaultAspect;
import com.enndfp.simpleframework.core.BeanContainer;
import com.enndfp.simpleframework.utils.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author Enndfp
 */
public class AspectWeaver {
    private final BeanContainer beanContainer;

    public AspectWeaver() {
        this.beanContainer = BeanContainer.getInstance();
    }

    public void doAOP() {
        // 1. 获取所有的切面类
        Set<Class<?>> aspectSet = beanContainer.getClassesByAnnotation(Aspect.class);
        // 2. 将切面类按照不同的织入目标进行分类
        Map<Class<? extends Annotation>, List<AspectInfo>> categorizedWeavingTargetMap = new HashMap<>();
        if (ValidationUtil.isEmpty(aspectSet)) return;
        for (Class<?> aspectClass : aspectSet) {
            if (!verifyAspect(aspectClass)) {
                throw new RuntimeException("@Aspect and @Order have not been added to the Aspect class, " +
                        "or Aspect class does not extend from DefaultAspect, or the value in Aspect Tag equals @Aspect");
            }
            categorizeAspect(categorizedWeavingTargetMap, aspectClass);
        }
        // 3. 按照不同的织入目标分别去按序织入Aspect
        if (ValidationUtil.isEmpty(categorizedWeavingTargetMap)) return;
        for (Class<? extends Annotation> weavingTarget : categorizedWeavingTargetMap.keySet()) {
            weaveByWeavingTarget(weavingTarget, categorizedWeavingTargetMap.get(weavingTarget));
        }
    }

    /**
     * 按照不同的织入目标分别去按序织入Aspect
     *
     * @param weavingTarget  织入目标
     * @param aspectInfoList 切面类的集合
     */
    private void weaveByWeavingTarget(Class<? extends Annotation> weavingTarget, List<AspectInfo> aspectInfoList) {
        // 1. 获取被代理类的集合
        Set<Class<?>> targetClassSet = beanContainer.getClassesByAnnotation(weavingTarget);
        if (ValidationUtil.isEmpty(targetClassSet)) return;
        // 2. 遍历被代理类，分别为每个被代理类生成动态代理
        for (Class<?> targetClass : targetClassSet) {
            // 创建动态代理对象
            AspectListExecutor aspectListExecutor = new AspectListExecutor(targetClass, aspectInfoList);
            Object proxyBean = ProxyCreator.createProxy(targetClass, aspectListExecutor);
            // 3. 将动态代理对象实例添加到容器中，取代未被代理前的类实例
            beanContainer.addBean(targetClass, proxyBean);
        }
    }

    /**
     * 将切面类按照不同的织入目标进行分类
     *
     * @param categorizedWeavingTargetMap 分类完成的织入目标Map
     * @param aspectClass                 待分类的切面类
     */
    private void categorizeAspect(Map<Class<? extends Annotation>, List<AspectInfo>> categorizedWeavingTargetMap, Class<?> aspectClass) {
        // 1. 获取标签值
        Order orderTag = aspectClass.getAnnotation(Order.class);
        Aspect aspectTag = aspectClass.getAnnotation(Aspect.class);
        // 2. 从容器中获取aspect实例
        DefaultAspect aspect = (DefaultAspect) beanContainer.getBean(aspectClass);
        AspectInfo aspectInfo = new AspectInfo(orderTag.value(), aspect);
        // 3. 将切面类加入到对应的位置的Map
        if (!categorizedWeavingTargetMap.containsKey(aspectTag.value())) {
            categorizedWeavingTargetMap.put(aspectTag.value(), new ArrayList<>());
        }
        categorizedWeavingTargetMap.get(aspectTag.value()).add(aspectInfo);
    }

    /**
     * 验证被Aspect标记的类是否满足条件
     *
     * @param aspectClass Aspect标记的类
     * @return
     */
    private boolean verifyAspect(Class<?> aspectClass) {
        return aspectClass.isAnnotationPresent(Aspect.class) &&
                aspectClass.isAnnotationPresent(Order.class) &&
                DefaultAspect.class.isAssignableFrom(aspectClass) &&
                aspectClass.getAnnotation(Aspect.class).value() != Aspect.class;
    }
}
