package com.enndfp.simpleframework.mvc.processor;

import com.enndfp.simpleframework.mvc.RequestProcessorChain;

/**
 * 请求执行器
 * @author Enndfp
 */
public interface RequestProcessor {

    boolean process(RequestProcessorChain requestProcessorChain) throws Exception;
}
