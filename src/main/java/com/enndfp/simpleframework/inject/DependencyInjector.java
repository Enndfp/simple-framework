package com.enndfp.simpleframework.inject;

import com.enndfp.simpleframework.core.BeanContainer;
import com.enndfp.simpleframework.inject.annotation.Autowired;
import com.enndfp.simpleframework.utils.ClassUtil;
import com.enndfp.simpleframework.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author Enndfp
 */
@Slf4j
public class DependencyInjector {

    // Bean容器
    private final BeanContainer beanContainer;

    public DependencyInjector() {
        beanContainer = BeanContainer.getInstance();
    }

    /**
     * 执行IOC
     */
    public void doIOC() {
        Set<Class<?>> classSet = beanContainer.getClasses();
        if (ValidationUtil.isEmpty(classSet)) {
            log.warn("empty classSet in beanContainer");
            return;
        }
        // 1. 遍历Bean容器中所有的Class对象
        for (Class<?> clazz : classSet) {
            // 2. 遍历Class对象的所有成员变量
            Field[] fields = clazz.getDeclaredFields();
            if (ValidationUtil.isEmpty(fields)) {
                continue;
            }
            for (Field field : fields) {
                // 3. 找出被Autowired标记的成员变量
                if (field.isAnnotationPresent(Autowired.class)) {
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    String autowiredValue = autowired.value();

                    // 4. 获取这些变量的类型
                    Class<?> fieldClass = field.getType();
                    // 5. 根据类型在容器中找到对应的实例
                    Object fieldInstance = getFieldInstance(fieldClass, autowiredValue);
                    if (fieldInstance == null) {
                        throw new RuntimeException("unable to inject relevant type, target fieldClass is :"
                                + fieldClass.getName() + "autowiredValue is " + autowiredValue);
                    }

                    // 6. 通过反射将对应的成员变量实例注入到相应位置
                    Object targetBean = beanContainer.getBean(clazz);
                    ClassUtil.setField(field, targetBean, fieldInstance, true);
                }
            }
        }

    }

    /**
     * 根据Class对象获取beanContainer里的实例
     *
     * @param fieldClass     Class对象
     * @param autowiredValue 指定注入的具体类型
     * @return Bean实例
     */
    private Object getFieldInstance(Class<?> fieldClass, String autowiredValue) {
        // 1. 从容器中获取bean
        Object fieldInstance = beanContainer.getBean(fieldClass);
        if (fieldInstance != null) return fieldInstance;

        // 2. 如果是接口类型则根据实现类获取bean
        Class<?> implementedClass = getImplementClass(fieldClass, autowiredValue);
        if (implementedClass == null) return null;
        return beanContainer.getBean(implementedClass);
    }

    /**
     * 根据Class对象获取接口的实现类
     *
     * @param fieldClass     Class对象
     * @param autowiredValue 指定注入的具体类型
     * @return 实现类
     */
    private Class<?> getImplementClass(Class<?> fieldClass, String autowiredValue) {
        Set<Class<?>> classSet = beanContainer.getClassesBySuper(fieldClass);
        if (ValidationUtil.isEmpty(classSet)) return null;

        // 针对有多个实现类，根据类型则无法判断注入哪个实例，则根据指定的类型值
        // 1. 用户没有指定类型值
        if (ValidationUtil.isEmpty(autowiredValue)) {
            // 有多个实现类，报错
            if (classSet.size() > 1) {
                throw new RuntimeException("multiple implemented classes for "
                        + fieldClass.getName() + " please set @Autowired's value to pick one: " + classSet);
            }
            // 只有一个实现类，返回
            return classSet.iterator().next();
        }
        // 2. 用户指定了类型值
        for (Class<?> clazz : classSet) {
            if (autowiredValue.equals(clazz.getSimpleName())) {
                return clazz;
            }
        }
        return null;
    }
}
