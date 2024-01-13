package com.enndfp.demo.controller.frontend;

import com.enndfp.demo.entity.dto.Result;
import com.enndfp.demo.service.combine.HeadLineShopCategoryCombineService;
import com.enndfp.demo.entity.dto.MainPageInfoDTO;
import com.enndfp.simpleframework.core.annotation.Controller;
import com.enndfp.simpleframework.inject.annotation.Autowired;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Enndfp
 */
@Controller
@Getter
public class MainPageController {
    @Autowired()
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
