package com.enndfp.demo.service.combine.impl;

import com.enndfp.demo.entity.bo.HeadLine;
import com.enndfp.demo.entity.bo.ShopCategory;
import com.enndfp.demo.entity.dto.MainPageInfoDTO;
import com.enndfp.demo.entity.dto.Result;
import com.enndfp.demo.service.combine.HeadLineShopCategoryCombineService;
import com.enndfp.demo.service.solo.HeadLineService;
import com.enndfp.demo.service.solo.ShopCategoryService;
import com.enndfp.simpleframework.core.annotation.Service;
import com.enndfp.simpleframework.inject.annotation.Autowired;

import java.util.List;

/**
 * @author Enndfp
 */
@Service
public class HeadLineShopCategoryCombineServiceImpl2 implements HeadLineShopCategoryCombineService {

    @Autowired
    private HeadLineService headLineService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Override
    public Result<MainPageInfoDTO> getMainPageInfo() {
        // 1. 获取头条列表
        HeadLine headLineCondition = new HeadLine();
        headLineCondition.setEnableStatus(1);
        Result<List<HeadLine>> headLineResult = headLineService.queryHeadLine(headLineCondition, 1, 4);

        // 2. 获取店铺类别列表
        ShopCategory shopCategoryCondition = new ShopCategory();
        Result<List<ShopCategory>> shopCategoryResult = shopCategoryService.queryShopCategory(shopCategoryCondition, 1, 100);

        // 3. 合并两者并返回
        Result<MainPageInfoDTO> result = mergeMainPageInfoResult(headLineResult, shopCategoryResult);
        return result;
    }

    private Result<MainPageInfoDTO> mergeMainPageInfoResult(Result<List<HeadLine>> headLineResult, Result<List<ShopCategory>> shopCategoryResult) {
        return null;
    }
}
