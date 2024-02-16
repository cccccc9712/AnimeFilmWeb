package controller;

import dal.userDao;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "loginControl", urlPatterns = {"/login"})
public class loginControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String mail = req.getParameter("mail");
        String password = req.getParameter("pass");

        if (!validateEmail(mail)) {
            req.setAttribute("failedLoginMessage", "Wrong email format!");
            req.getRequestDispatcher("SignIn.jsp").forward(req, resp);
            return;
        }

        if (mail.isBlank() || password.isBlank()) {
            req.setAttribute("failedLoginMessage", "Please input to sign in!");
            req.getRequestDispatcher("SignIn.jsp").forward(req, resp);
            return;
        }

        userDao dao = new userDao();
        User user = dao.login(mail, password);
        if (user == null) {
            req.setAttribute("failedLoginMessage", "Wrong Gmail or Password!");
            req.getRequestDispatcher("SignIn.jsp").forward(req, resp);
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userSession", user);
            resp.sendRedirect("home");
        }
    }

    public static boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }
}
