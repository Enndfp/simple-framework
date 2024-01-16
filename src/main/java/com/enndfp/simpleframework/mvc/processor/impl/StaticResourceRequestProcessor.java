package com.enndfp.simpleframework.mvc.processor.impl;

import com.enndfp.simpleframework.mvc.RequestProcessorChain;
import com.enndfp.simpleframework.mvc.processor.RequestProcessor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * 静态资源请求处理，包括但不限于图片、css以及js文件等
 *
 * @author Enndfp
 */
public class StaticResourceRequestProcessor implements RequestProcessor {

    private static final String DEFAULT_SERVLET = "default";

    private static final String STATIC_RESOURCE_PREFIX = "/static/";

    // tomcat默认请求派发器RequestDispatcher的名称
    private final RequestDispatcher defaultDispatcher;

    public StaticResourceRequestProcessor(ServletContext servletContext) {
        this.defaultDispatcher = servletContext.getNamedDispatcher(DEFAULT_SERVLET);
        if (null == defaultDispatcher) {
            throw new RuntimeException("There is no default tomcat servlet");
        }
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        // 1. 通过请求路径判断请求的是不是静态资源 webapp/static
        if (isStaticResource(requestProcessorChain.getRequestPath())) {
            // 2. 如果是静态资源，则将请求转发给 defaultDispatcher 处理
            defaultDispatcher.forward(requestProcessorChain.getRequest(), requestProcessorChain.getResponse());
            return false;
        }
        return true;
    }

    /**
     * 通过请求路径前缀（目录）判断是否是静态资源 /static/
     *
     * @param requestPath
     * @return
     */
    private boolean isStaticResource(String requestPath) {
        return requestPath.startsWith(STATIC_RESOURCE_PREFIX);
    }
}
