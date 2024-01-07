package com.enndfp.controller.frontend;

import com.enndfp.entity.dto.MainPageInfoDTO;
import com.enndfp.entity.dto.Result;
import com.enndfp.service.combine.HeadLineShopCategoryCombineService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Enndfp
 */
public class MainPageController {
    private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;

    /**
     * 获取主页信息
     *
     * @param req
     * @param resp
     * @return
     */
    public Result<MainPageInfoDTO> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp) {
        return headLineShopCategoryCombineService.getMainPageInfo();
    }
}
