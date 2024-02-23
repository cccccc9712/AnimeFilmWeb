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
import java.sql.Date;

@WebServlet(name = "premiumRegisterControl", urlPatterns = "/RegisterPremium")
public class premiumRegisterControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("userSession");

        if (user == null) {
            resp.sendRedirect("SignIn.jsp");
            return;
        }

        userDao ud = new userDao();
        try {
            long currentTime = System.currentTimeMillis();
            Date registeredDate = new Date(currentTime);
            Date outOfDate = new Date(currentTime + (30L * 24 * 60 * 60 * 1000)); // Thêm 30 ngày

            boolean registeredSuccessfully = ud.registerPremium(user.getUserId(), "Premium", registeredDate, outOfDate);
            if (registeredSuccessfully) {
                req.setAttribute("errorMessage", "Buy succesfully.");
                resp.sendRedirect("pricing.jsp");
            } else {
                req.setAttribute("errorMessage", "Error registering for premium. Please try again.");
                req.getRequestDispatcher("pricing.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "System error occurred. Please contact support.");
            req.getRequestDispatcher("pricing.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
