package com.enndfp.controller.superadmin;

import com.enndfp.entity.bo.ShopCategory;
import com.enndfp.entity.dto.Result;
import com.enndfp.service.solo.ShopCategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Enndfp
 */
public class ShopCategoryOperationController {

    private ShopCategoryService shopCategoryService;

    /**
     * 添加店铺类别
     *
     * @param req
     * @param resp
     * @return
     */
    public Result<Boolean> addShopCategory(HttpServletRequest req, HttpServletResponse resp) {
        return shopCategoryService.addShopCategory(new ShopCategory());
    }

    /**
     * 删除店铺类别
     *
     * @param req
     * @param resp
     * @return
     */
    public Result<Boolean> removeShopCategory(HttpServletRequest req, HttpServletResponse resp) {
        return shopCategoryService.removeShopCategory(1);
    }

    /**
     * 修改店铺类别
     *
     * @param req
     * @param resp
     * @return
     */
    public Result<Boolean> modifyShopCategory(HttpServletRequest req, HttpServletResponse resp) {
        return shopCategoryService.modifyShopCategory(new ShopCategory());
    }

    /**
     * 查询店铺类别
     *
     * @param req
     * @param resp
     * @return
     */
    public Result<ShopCategory> queryShopCategoryById(HttpServletRequest req, HttpServletResponse resp) {
        return shopCategoryService.queryShopCategoryById(1);
    }

    /**
     * 查询店铺类别列表
     *
     * @param req
     * @param resp
     * @return
     */
    public Result<List<ShopCategory>> queryShopCategory(HttpServletRequest req, HttpServletResponse resp) {
        return shopCategoryService.queryShopCategory(new ShopCategory(), 1, 100);
    }
}
