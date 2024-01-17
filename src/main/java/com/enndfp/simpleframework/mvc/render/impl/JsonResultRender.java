package com.enndfp.simpleframework.mvc.render.impl;

import com.enndfp.simpleframework.mvc.RequestProcessorChain;
import com.enndfp.simpleframework.mvc.render.ResultRender;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * json渲染器
 *
 * @author Enndfp
 */
public class JsonResultRender implements ResultRender {

    private Object result;

    public JsonResultRender(Object result) {
        this.result = result;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        // 1. 设置响应头
        HttpServletResponse response = requestProcessorChain.getResponse();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 2. 将经过Gson处理后的结果写入响应流，try-with-resources 语句确保在语句结束时自动关闭每个资源
        try (PrintWriter writer = response.getWriter()) {
            Gson gson = new Gson();
            writer.write(gson.toJson(result));
            // flush()不是必需的，因为close()将被自动调用
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
