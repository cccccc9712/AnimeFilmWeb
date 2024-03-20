package controller;

import dal.userDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "changePasswordControl", urlPatterns = "/changePassword")
public class changePasswordControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        String cfPassword = req.getParameter("confirmPassword");
        String gmail = (String) req.getSession().getAttribute("userEmail");

        if (password.isBlank() || cfPassword.isBlank()) {
            req.setAttribute("errorMessage", "Please input to continue!");
            req.getRequestDispatcher("changePasswordForm.jsp").forward(req, resp);
            return;
        }

        if (!validatePassword(password)){
            req.setAttribute("errorMessage", "Password must contain 8 characters with \nthe first letters uppercase.");
            req.getRequestDispatcher("changePasswordForm.jsp").forward(req, resp);
            return;
        }

        if(password.equals(cfPassword)) {
            try {
                userDao userDao = new userDao();
                boolean success = userDao.changePassword(gmail, password);
                if (success) {
                    req.setAttribute("failedLoginMessage", "Reset Password Successfully");
                    req.getRequestDispatcher("SignIn.jsp").forward(req, resp);
                } else {
                    req.setAttribute("errorMessage", "An error occurred. Please try again.");
                    req.getRequestDispatcher("changePasswordForm.jsp").forward(req, resp);
                }
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("errorMessage", "An error occurred. Please try again.");
                req.getRequestDispatcher("changePasswordForm.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("errorMessage", "Passwords do not match. Please try again.");
            req.getRequestDispatcher("changePasswordForm.jsp").forward(req, resp);
        }

    }

    public static boolean validatePassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        char firstChar = password.charAt(0);
        return Character.isUpperCase(firstChar);
    }
}
