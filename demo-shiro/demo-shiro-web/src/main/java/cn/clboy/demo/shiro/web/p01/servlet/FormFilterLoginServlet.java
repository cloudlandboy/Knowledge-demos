package cn.clboy.demo.shiro.web.p01.servlet;

import cn.clboy.demo.shiro.web.p05.utils.AuthenticationFailedUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "formFilterLoginServlet", urlPatterns = "/formfilterlogin")
public class FormFilterLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String errorClassName = (String) req.getAttribute("shiroLoginFailure");

        if (errorClassName != null) {
            req.setAttribute("error", AuthenticationFailedUtil.getMsg(errorClassName));
        }
        req.getRequestDispatcher("/WEB-INF/jsp/formfilterlogin.jsp").forward(req, resp);
    }
}