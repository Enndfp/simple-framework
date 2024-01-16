package com.enndfp.simpleframework.mvc.render;

import com.enndfp.simpleframework.mvc.RequestProcessorChain;

/**
 * 渲染请求结果
 *
 * @author Enndfp
 */
public interface ResultRender {

    void render(RequestProcessorChain requestProcessorChain) throws Exception;
}
