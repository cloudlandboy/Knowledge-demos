package cn.clboy.demo.shiro.web.p04.servlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "sessionServlet", urlPatterns = "/session")
public class SessionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //debug
        HttpSession session = req.getSession();
        //销毁session
        session.invalidate();
        resp.getWriter().write("debug model");
    }
}