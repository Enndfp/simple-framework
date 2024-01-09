package com.enndfp.demo.service.combine;

import com.enndfp.demo.entity.dto.Result;
import com.enndfp.demo.entity.dto.MainPageInfoDTO;

/**
 * @author Enndfp
 */
public interface HeadLineShopCategoryCombineService {

    /**
     * 获取主页信息
     *
     * @return
     */
    Result<MainPageInfoDTO> getMainPageInfo();
}
