package com.enndfp.demo.controller.frontend;

import com.enndfp.demo.entity.dto.Result;
import com.enndfp.demo.service.combine.HeadLineShopCategoryCombineService;
import com.enndfp.demo.entity.dto.MainPageInfoDTO;

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
