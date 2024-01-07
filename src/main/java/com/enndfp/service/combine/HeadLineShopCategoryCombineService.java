package com.enndfp.service.combine;

import com.enndfp.entity.dto.MainPageInfoDTO;
import com.enndfp.entity.dto.Result;

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
