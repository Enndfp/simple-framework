package com.enndfp.simpleframework.core;

import com.enndfp.simpleframework.aop.annotation.Aspect;
import com.enndfp.simpleframework.core.annotation.Component;
import com.enndfp.simpleframework.core.annotation.Controller;
import com.enndfp.simpleframework.core.annotation.Repository;
import com.enndfp.simpleframework.core.annotation.Service;
import com.enndfp.simpleframework.utils.ClassUtil;
import com.enndfp.simpleframework.utils.ValidationUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Enndfp
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {

    // 存放所有被标记的目标对象
    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap<>();

    // 加载Bean的注解列表
    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION
            = Arrays.asList(Component.class, Controller.class, Service.class, Repository.class, Aspect.class);

    // 容器是否已经加载过Bean
    private boolean loaded = false;

    /**
     * 枚举类型的饿汉式单例
     */
    private enum ContainerHolder {
        HOLDER;
        private final BeanContainer instance;

        ContainerHolder() {
            instance = new BeanContainer();
        }
    }

    /**
     * 获取是否已经加载过Bean
     *
     * @return
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * 获取Bean容器实例
     *
     * @return
     */
    public static BeanContainer getInstance() {
        return ContainerHolder.HOLDER.instance;
    }

    /**
     * 获取Bean实例的数量
     *
     * @return
     */
    public int size() {
        return beanMap.size();
    }

    /**
     * 扫描加载所有Bean
     *
     * @param packageName 包名
     */
    public synchronized void loadBeans(String packageName) {
        // 1. 判断容器是否已经加载过
        if (isLoaded()) {
            log.warn("BeanContainer has been loaded.");
            return;
        }

        // 2. 获取指定包名的class对象集合
        Set<Class<?>> classSet = ClassUtil.extractPackageClass(packageName);
        if (ValidationUtil.isEmpty(classSet)) {
            log.warn("extract nothing from packageName " + packageName);
            return;
        }

        // 3. 实例化指定注解的class对象
        for (Class<?> clazz : classSet) {
            for (Class<? extends Annotation> annotation : BEAN_ANNOTATION) {
                // 类上面标记了定义的注解
                if (clazz.isAnnotationPresent(annotation)) {
                    // 将目标类作为键，对应的实例作为值，放入到beanMap中
                    beanMap.put(clazz, ClassUtil.newInstance(clazz, true));
                }
            }
        }

        // 4. 修改容器是否被加载的标记
        loaded = true;
    }

    /**
     * 添加Bean实例
     *
     * @param clazz Class对象
     * @param bean  Bean实例
     * @return 添加的Bean实例，没有则返回null
     */
    public Object addBean(Class<?> clazz, Object bean) {
        return beanMap.put(clazz, bean);
    }

    /**
     * 删除Bean实例
     *
     * @param clazz Class对象
     * @return 删除的Bean实例，没有则返回null
     */
    public Object removeBean(Class<?> clazz) {
        return beanMap.remove(clazz);
    }

    /**
     * 获取Bean实例
     *
     * @param clazz Class对象
     * @return Bean实例
     */
    public Object getBean(Class<?> clazz) {
        return beanMap.get(clazz);
    }

    /**
     * 获取所有Class对象的集合
     *
     * @return Class集合
     */
    public Set<Class<?>> getClasses() {
        return beanMap.keySet();
    }

    /**
     * 获取所有Bean集合
     *
     * @return Bean集合
     */
    public Set<Object> getBeans() {
        return new HashSet<>(beanMap.values());
    }

    /**
     * 根据注解获取Bean的Class集合
     *
     * @param annotation 注解
     * @return Class集合
     */
    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation) {
        // 1. 获取beanMap的所有class对象
        Set<Class<?>> keySet = getClasses();
        if (ValidationUtil.isEmpty(keySet)) {
            log.warn("nothing in beanMap");
            return null;
        }

        // 2. 获取指定注解的class对象，并添加到classSet中
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet) {
            if (clazz.isAnnotationPresent(annotation)) {
                classSet.add(clazz);
            }
        }
        return classSet.size() > 0 ? classSet : null;
    }

    /**
     * 通过接口或者父类获取实现类或者子类的Class集合，不包括其本身
     *
     * @param interfaceOrClass 接口或者父类
     * @return Class集合
     */
    public Set<Class<?>> getClassesBySuper(Class<?> interfaceOrClass) {
        // 1. 获取beanMap的所有class对象
        Set<Class<?>> keySet = getClasses();
        if (ValidationUtil.isEmpty(keySet)) {
            log.warn("nothing in beanMap");
            return null;
        }

        // 2. 获取指定接口或父类的class对象，并添加到classSet中
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet) {
            if (interfaceOrClass.isAssignableFrom(clazz) && !clazz.equals(interfaceOrClass)) {
                classSet.add(clazz);
            }
        }
        return classSet.size() > 0 ? classSet : null;
    }
}
