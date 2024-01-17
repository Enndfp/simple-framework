package com.enndfp.simpleframework.mvc.render.impl;

import com.enndfp.simpleframework.mvc.RequestProcessorChain;
import com.enndfp.simpleframework.mvc.render.ResultRender;
import com.enndfp.simpleframework.mvc.type.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 页面渲染器
 *
 * @author Enndfp
 */
public class ViewResultRender implements ResultRender {

    public static final String VIEW_PATH = "/templates/";

    private ModelAndView modelAndView;

    /**
     * 对传入的参数进行处理，并赋值给ModelAndView成员变量
     *
     * @param mv
     */
    public ViewResultRender(Object mv) {
        if (mv instanceof ModelAndView) {
            // 1. 如果入参类型是ModelAndView，则直接赋值给成员变量
            this.modelAndView = (ModelAndView) mv;
        } else if (mv instanceof String) {
            // 2. 如果入参类型是String，则为视图，需要包装后才赋值给成员变量
            this.modelAndView = new ModelAndView().setViewPath((String) mv);
        } else {
            // 3. 针对其他情况，则直接抛出异常
            throw new RuntimeException("illegal request result type");
        }
    }

    /**
     * 根据请求结果按照视图路径转发至对应视图进行展示
     *
     * @param requestProcessorChain
     * @throws Exception
     */
    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        HttpServletRequest request = requestProcessorChain.getRequest();
        HttpServletResponse response = requestProcessorChain.getResponse();

        String viewPath = modelAndView.getViewPath();
        Map<String, Object> viewData = modelAndView.getViewData();
        for (Map.Entry<String, Object> entry : viewData.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        request.getRequestDispatcher(VIEW_PATH + viewPath).forward(request, response);
    }
}
