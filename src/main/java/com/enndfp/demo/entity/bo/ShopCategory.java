package com.enndfp.demo.entity.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author Enndfp
 */
@Data
public class ShopCategory {

    private Long shopCategoryId;

    private String shopCategoryName;

    private String shopCategoryDesc;

    private String shopCategoryImg;

    private Integer priority;

    private Date createTime;

    private Date lastEditTime;

    private ShopCategory parent;
}