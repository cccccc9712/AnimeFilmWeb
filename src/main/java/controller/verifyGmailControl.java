package controller;

import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java_api.gmailVerify_api;

import java.io.IOException;

@WebServlet(name = "verifyGmailControl", urlPatterns = "/verify")
public class verifyGmailControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("mail");

        if (email.isBlank()) {
            req.setAttribute("errorMessage", "Please input to continue!");
            req.getRequestDispatcher("gmailVerifyForm.jsp").forward(req, resp);
            return;
        }

        if (!validateEmail(email)) {
            req.setAttribute("errorMessage", "Wrong email format!");
            req.getRequestDispatcher("gmailVerifyForm.jsp").forward(req, resp);
            return;
        }

        User user = new User();
        user.setUserGmail(email);

        gmailVerify_api gmailApi = new gmailVerify_api();
        String verificationCode = gmailApi.getRandom();

        boolean emailSent = gmailApi.sendEmail(user, verificationCode);

        if(emailSent) {
            req.getSession().setAttribute("userEmail", email);
            req.getSession().setAttribute("verificationCode", verificationCode);
            resp.sendRedirect("otpConfirmForm.jsp");
        } else {
            req.setAttribute("errorMessage", "Failed to send verification email. Please try again.");
            req.getRequestDispatcher("gmailVerifyForm.jsp").forward(req, resp);
        }
    }

    public static boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }
}
