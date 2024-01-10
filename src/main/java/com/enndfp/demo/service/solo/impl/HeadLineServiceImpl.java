package com.enndfp.demo.service.solo.impl;

import com.enndfp.demo.entity.bo.HeadLine;
import com.enndfp.demo.entity.dto.Result;
import com.enndfp.demo.service.solo.HeadLineService;
import com.enndfp.simpleframework.core.annotation.Service;

import java.util.List;

/**
 * @author Enndfp
 */
@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Override
    public Result<Boolean> addHeadLine(HeadLine headLine) {
        return null;
    }

    @Override
    public Result<Boolean> removeHeadLine(int headLineId) {
        return null;
    }

    @Override
    public Result<Boolean> modifyHeadLine(HeadLine headLine) {
        return null;
    }

    @Override
    public Result<HeadLine> queryHeadLineById(int headLineId) {
        return null;
    }

    @Override
    public Result<List<HeadLine>> queryHeadLine(HeadLine headLineCondition, int pageIndex, int pageSize) {
        return null;
    }
}
