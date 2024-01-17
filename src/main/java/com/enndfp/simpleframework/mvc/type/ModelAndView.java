package com.enndfp.simpleframework.mvc.type;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储处理完后的结果数据以及显示该数据的视图
 *
 * @author Enndfp
 */
@Getter
public class ModelAndView {

    // 页面所在的路径
    private String viewPath;

    // 页面的data数据
    private Map<String, Object> viewData = new HashMap<>();

    public ModelAndView setViewPath(String viewPath) {
        this.viewPath = viewPath;
        return this;
    }

    public ModelAndView addViewData(String attributeName, Object attributeValue) {
        viewData.put(attributeName, attributeValue);
        return this;
    }
}
