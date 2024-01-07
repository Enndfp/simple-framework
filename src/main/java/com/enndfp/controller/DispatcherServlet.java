package com.enndfp.controller;

import com.enndfp.controller.frontend.MainPageController;
import com.enndfp.controller.superadmin.HeadLineOperationController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Enndfp
 */
@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("request path is: " + req.getServletPath());
        System.out.println("request method is: " + req.getMethod());
        if (req.getServletPath().equals("/frontend/getmainpageinfo") && req.getMethod().equals("GET")) {
            new MainPageController().getMainPageInfo(req, resp);
        } else if (req.getServletPath().equals("/superadmin/addheadline") && req.getMethod().equals("POST")) {
            new HeadLineOperationController().addHeadLine(req, resp);
        }
    }
}
