package com.enndfp.simpleframework.mvc.processor.impl;

import com.enndfp.simpleframework.mvc.RequestProcessorChain;
import com.enndfp.simpleframework.mvc.processor.RequestProcessor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * jsp资源请求处理
 *
 * @author Enndfp
 */
public class JspRequestProcessor implements RequestProcessor {

    private static final String JSP_SERVLET = "jsp";

    private static final String JSP_RESOURCE_PREFIX = "/templates/";

    // 处理jsp资源的RequestDispatcher
    private final RequestDispatcher jspDispatcher;

    public JspRequestProcessor(ServletContext servletContext) {
        jspDispatcher = servletContext.getNamedDispatcher(JSP_SERVLET);
        if (null == jspDispatcher) {
            throw new RuntimeException("there is no jsp servlet");
        }
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        // 1. 通过请求路径判断请求的是不是jsp资源 webapp/templates
        if (isJspResource(requestProcessorChain.getRequestPath())) {
            // 2. 如果是jsp资源，则将请求转发给 jspDispatcher 处理
            jspDispatcher.forward(requestProcessorChain.getRequest(), requestProcessorChain.getResponse());
            return false;
        }
        return true;
    }

    /**
     * 通过请求路径前缀（目录）判断是否是jsp资源 /templates/
     *
     * @param requestPath
     * @return
     */
    private boolean isJspResource(String requestPath) {
        return requestPath.startsWith(JSP_RESOURCE_PREFIX);
    }
}
