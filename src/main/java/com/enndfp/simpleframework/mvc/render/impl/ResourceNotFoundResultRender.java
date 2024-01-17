package com.enndfp.simpleframework.mvc.render.impl;

import com.enndfp.simpleframework.mvc.RequestProcessorChain;
import com.enndfp.simpleframework.mvc.render.ResultRender;

import javax.servlet.http.HttpServletResponse;

/**
 * 资源找不到时使用的渲染器
 *
 * @author Enndfp
 */
public class ResourceNotFoundResultRender implements ResultRender {

    private String requestMethod;

    private String requestPath;

    public ResourceNotFoundResultRender(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        int notFoundCode = HttpServletResponse.SC_NOT_FOUND;
        requestProcessorChain.getResponse().sendError(notFoundCode,
                "获取不到对应的请求资源：请求路径[" + requestPath + "]，" + "请求方法[" + requestMethod + "]");
    }
}
