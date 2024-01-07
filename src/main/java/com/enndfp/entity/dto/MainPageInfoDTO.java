package com.enndfp.entity.dto;

import com.enndfp.entity.bo.HeadLine;
import com.enndfp.entity.bo.ShopCategory;
import lombok.Data;

import java.util.List;

/**
 * @author Enndfp
 */
@Data
public class MainPageInfoDTO {

    // 头条列表
    private List<HeadLine> headLineList;

    // 店铺类别列表
    private List<ShopCategory> shopCategoryList;
}
