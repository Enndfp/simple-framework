package com.enndfp.service.solo;

import com.enndfp.entity.bo.HeadLine;
import com.enndfp.entity.dto.Result;

import java.util.List;

/**
 * @author Enndfp
 */
public interface HeadLineService {
    /**
     * 添加头条
     *
     * @param headLine
     * @return
     */
    Result<Boolean> addHeadLine(HeadLine headLine);

    /**
     * 删除头条
     *
     * @param headLineId
     * @return
     */
    Result<Boolean> removeHeadLine(int headLineId);

    /**
     * 修改头条
     *
     * @param headLine
     * @return
     */
    Result<Boolean> modifyHeadLine(HeadLine headLine);

    /**
     * 查询头条
     *
     * @param headLineId
     * @return
     */
    Result<HeadLine> queryHeadLineById(int headLineId);

    /**
     * 查询头条列表
     *
     * @param headLineCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Result<List<HeadLine>> queryHeadLine(HeadLine headLineCondition, int pageIndex, int pageSize);
}
