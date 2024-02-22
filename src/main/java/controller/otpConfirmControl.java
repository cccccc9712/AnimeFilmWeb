package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "otpConfirmControl", urlPatterns = "/confirm")
public class otpConfirmControl  extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userCode = req.getParameter("code");
        String savedCode = (String) req.getSession().getAttribute("verificationCode");

        if (userCode.isBlank()) {
            req.setAttribute("errorMessage", "Please input to continue!");
            req.getRequestDispatcher("otpConfirmForm.jsp").forward(req, resp);
            return;
        }

        if(userCode != null && userCode.equals(savedCode)) {
            resp.sendRedirect("changePasswordForm.jsp");
        } else {
            req.setAttribute("errorMessage", "Invalid verification code. Please try again.");
            req.getRequestDispatcher("otpConfirmForm.jsp").forward(req, resp);
        }
    }
}
