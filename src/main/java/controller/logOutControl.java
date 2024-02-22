package controller;

import dal.userDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "logOutControl", urlPatterns = "/logout")
public class logOutControl extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId != null) {
                userDao ud = new userDao();
                ud.deleteRememberToken(userId); // Xóa token từ DB chỉ khi userId không null
            }
            session.invalidate(); // Hủy session
        }

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("rememberMe".equals(cookie.getName())) {
                    Cookie newcookie = new Cookie(cookie.getName(), null);
                    String cookiePath = req.getContextPath();
                    cookie.setMaxAge(0);
                    if (cookiePath.isEmpty()) {
                        cookie.setPath("/");
                    } else {
                        cookie.setPath(cookiePath);
                    }
                    resp.addCookie(newcookie);
                    break;
                }
            }
        }
        resp.sendRedirect("home");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }
}
