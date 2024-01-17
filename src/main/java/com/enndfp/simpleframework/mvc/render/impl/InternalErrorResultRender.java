package com.enndfp.simpleframework.mvc.render.impl;

import com.enndfp.simpleframework.mvc.RequestProcessorChain;
import com.enndfp.simpleframework.mvc.render.ResultRender;

import javax.servlet.http.HttpServletResponse;

/**
 * 内部异常渲染器
 *
 * @author Enndfp
 */
public class InternalErrorResultRender implements ResultRender {

    private String errorMsg;

    public InternalErrorResultRender(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        int serverErrorCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        requestProcessorChain.getResponse().sendError(serverErrorCode, errorMsg);
    }
}
