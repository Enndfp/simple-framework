package com.enndfp.simpleframework.mvc;

import com.enndfp.simpleframework.aop.AspectWeaver;
import com.enndfp.simpleframework.core.BeanContainer;
import com.enndfp.simpleframework.inject.DependencyInjector;
import com.enndfp.simpleframework.mvc.processor.RequestProcessor;
import com.enndfp.simpleframework.mvc.processor.impl.ControllerRequestProcessor;
import com.enndfp.simpleframework.mvc.processor.impl.JspRequestProcessor;
import com.enndfp.simpleframework.mvc.processor.impl.PreRequestProcessor;
import com.enndfp.simpleframework.mvc.processor.impl.StaticResourceRequestProcessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enndfp
 */
@WebServlet("/*")
public class DispatcherServlet extends HttpServlet {

    List<RequestProcessor> PROCESSOR = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        // 1. 初始化容器
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.enndfp.demo");
        new AspectWeaver().doAOP();
        new DependencyInjector().doIOC();
        // 2. 初始化请求处理器责任链
        PROCESSOR.add(new PreRequestProcessor());
        PROCESSOR.add(new StaticResourceRequestProcessor(getServletContext()));
        PROCESSOR.add(new JspRequestProcessor(getServletContext()));
        PROCESSOR.add(new ControllerRequestProcessor());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 创建责任链实例对象
        RequestProcessorChain requestProcessorChain = new RequestProcessorChain(PROCESSOR.iterator(), req, resp);
        // 2. 通过责任链模式来依次调用请求处理器对请求进行处理
        requestProcessorChain.doRequestProcessorChain();
        // 3. 对处理结果进行渲染
        requestProcessorChain.doRender();
    }
}
