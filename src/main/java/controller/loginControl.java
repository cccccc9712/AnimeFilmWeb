package controller;

import dal.Dao;
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
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html;charset=UTF-8");
        String mail = req.getParameter("mail");
        String password = req.getParameter("pass");
        if (!mail.isBlank() || !password.isBlank()){
        Dao dao = new Dao();
        User user = dao.login(mail, password);
        if (user == null) {
            req.setAttribute("failedLoginMessage", "Wrong Gmail or Password!");
            req.getRequestDispatcher("SignIn.jsp").forward(req, resp);
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("userSession", user);
            resp.sendRedirect("index.jsp");
        }}else {
            req.setAttribute("failedLoginMessage", "Please input to sign in!");
            req.getRequestDispatcher("SignIn.jsp").forward(req, resp);
        }

    }
}
