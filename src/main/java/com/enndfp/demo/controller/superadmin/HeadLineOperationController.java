package com.enndfp.demo.controller.superadmin;

import com.enndfp.demo.entity.bo.HeadLine;
import com.enndfp.demo.entity.dto.Result;
import com.enndfp.demo.service.solo.HeadLineService;
import com.enndfp.simpleframework.core.annotation.Controller;
import com.enndfp.simpleframework.inject.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Enndfp
 */
@Controller
public class HeadLineOperationController {

    @Autowired
    private HeadLineService headLineService;

    /**
     * 添加头条
     *
     * @param req
     * @param resp
     * @return
     */
    public Result<Boolean> addHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        return headLineService.addHeadLine(new HeadLine());
    }

    /**
     * 删除头条
     *
     * @param req
     * @param resp
     * @return
     */
    public Result<Boolean> removeHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        return headLineService.removeHeadLine(1);
    }

    /**
     * 修改头条
     *
     * @param req
     * @param resp
     * @return
     */
    public Result<Boolean> modifyHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        return headLineService.modifyHeadLine(new HeadLine());
    }

    /**
     * 查询头条
     *
     * @param req
     * @param resp
     * @return
     */
    public Result<HeadLine> queryHeadLineById(HttpServletRequest req, HttpServletResponse resp) {
        return headLineService.queryHeadLineById(1);
    }

    /**
     * 查询头条列表
     *
     * @param req
     * @param resp
     * @return
     */
    public Result<List<HeadLine>> queryHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        return headLineService.queryHeadLine(null, 1, 100);
    }
}
