package cn.clboy.demo.springboot.embedded.web.servers.Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author clboy
 * @Date 2021/2/24 上午10:56
 * @Since 1.0.0
 */
@WebServlet(name = "demoRegisterServlet02", urlPatterns = "/regServlet02")
public class DemoRegisterServlet02 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("通过注解扫描的方式注册的Servlet - " + this.getServletName());
    }
}