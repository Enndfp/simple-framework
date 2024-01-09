package com.enndfp.demo.service.solo;

import com.enndfp.demo.entity.bo.ShopCategory;
import com.enndfp.demo.entity.dto.Result;

import java.util.List;

/**
 * @author Enndfp
 */
public interface ShopCategoryService {
    /**
     * 添加店铺类别
     *
     * @param shopCategory
     * @return
     */
    Result<Boolean> addShopCategory(ShopCategory shopCategory);

    /**
     * 删除店铺类别
     *
     * @param shopCategoryId
     * @return
     */
    Result<Boolean> removeShopCategory(int shopCategoryId);

    /**
     * 修改店铺类别
     *
     * @param shopCategory
     * @return
     */
    Result<Boolean> modifyShopCategory(ShopCategory shopCategory);

    /**
     * 查询店铺类别
     *
     * @param shopCategoryId
     * @return
     */
    Result<ShopCategory> queryShopCategoryById(int shopCategoryId);

    /**
     * 查询店铺类别列表
     *
     * @param shopCategoryCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Result<List<ShopCategory>> queryShopCategory(ShopCategory shopCategoryCondition, int pageIndex, int pageSize);
}
