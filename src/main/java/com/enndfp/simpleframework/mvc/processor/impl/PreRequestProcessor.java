package com.enndfp.simpleframework.mvc.processor.impl;

import com.enndfp.simpleframework.mvc.RequestProcessorChain;
import com.enndfp.simpleframework.mvc.processor.RequestProcessor;

/**
 * 请求预处理，包括编码以及路径处理
 *
 * @author Enndfp
 */
public class PreRequestProcessor implements RequestProcessor {
    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        // 1. 设置请求编码，将其统一设置成UTF-8
        requestProcessorChain.getRequest().setCharacterEncoding("UTF-8");

        // 2. 将请求路径末尾的 / 剔除，为后续匹配Controller请求路径做准备
        // 一般Controller的处理路径是/aaa/bbb，所以如果传入的路径结尾是/aaa/bbb/，就需要处理成/aaa/bbb
        String requestPath = requestProcessorChain.getRequestPath();
        //http://localhost:8080/simple-framework requestPath="/"
        if (requestPath.length() > 1 && requestPath.endsWith("/")) {
            requestProcessorChain.setRequestPath(requestPath.substring(0, requestPath.length() - 1));
        }
        return true;
    }
}
