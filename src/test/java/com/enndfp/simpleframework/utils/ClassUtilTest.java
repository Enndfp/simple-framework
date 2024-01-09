package com.enndfp.simpleframework.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * @author Enndfp
 */
public class ClassUtilTest {

    @Test
    public void extractPackageClassTest() {
        Set<Class<?>> classSet = ClassUtil.extractPackageClass("com.enndfp.demo.entity");
        System.out.println(classSet);
        Assertions.assertEquals(4, classSet.size());
    }
}
