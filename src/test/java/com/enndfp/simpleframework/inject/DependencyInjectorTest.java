package com.enndfp.simpleframework.inject;

import com.enndfp.demo.controller.frontend.MainPageController;
import com.enndfp.demo.service.combine.impl.HeadLineShopCategoryCombineServiceImpl;
import com.enndfp.simpleframework.core.BeanContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Enndfp
 */
public class DependencyInjectorTest {

    @Test
    public void doIOCTest() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.enndfp.demo");
        Assertions.assertEquals(true,beanContainer.isLoaded());
        MainPageController mainPageController = (MainPageController) beanContainer.getBean(MainPageController.class);
        Assertions.assertEquals(true, mainPageController instanceof MainPageController);
        Assertions.assertEquals(null,mainPageController.getHeadLineShopCategoryCombineService());
        new DependencyInjector().doIOC();
        Assertions.assertNotEquals(null,mainPageController.getHeadLineShopCategoryCombineService());
        Assertions.assertEquals(true,mainPageController.getHeadLineShopCategoryCombineService() instanceof HeadLineShopCategoryCombineServiceImpl);
    }
}