package com.enndfp.simpleframework.aop;

import com.enndfp.demo.controller.frontend.MainPageController;
import com.enndfp.simpleframework.core.BeanContainer;
import com.enndfp.simpleframework.inject.DependencyInjector;
import org.junit.jupiter.api.Test;

/**
 * @author Enndfp
 */
public class AspectWeaverTest {

    @Test
    void doAOPTest() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.enndfp.demo");
        new AspectWeaver().doAOP();
        new DependencyInjector().doIOC();
        MainPageController mainPageController = (MainPageController) beanContainer.getBean(MainPageController.class);
        mainPageController.getMainPageInfo(null, null);
    }
}