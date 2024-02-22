package controller;

import dal.userDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "changePasswordControl", urlPatterns = "/changepassword")
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
            req.setAttribute("errorMessage", "Password must contain 8 characters with the first letters uppercase.");
            req.getRequestDispatcher("changePasswordForm.jsp").forward(req, resp);
            return;
        }

        if(password != null && password.equals(cfPassword)) {
            // Nếu trùng khớp, gọi DAO để thay đổi mật khẩu
            try {
                // Giả sử bạn có một class DAO với phương thức để thay đổi mật khẩu
                userDao userDao = new userDao();
                boolean success = userDao.changePassword(gmail, password);
                if (success) {
                    // Chuyển hướng người dùng đến trang đăng nhập hoặc thông báo thành công
                    req.setAttribute("failedLoginMessage", "Reset Password Successfully");
                    req.getRequestDispatcher("SignIn.jsp").forward(req, resp);
                } else {
                    req.setAttribute("errorMessage", "An error occurred. Please try again.");
                    req.getRequestDispatcher("changePasswordForm.jsp").forward(req, resp);
                    return;
                }
            } catch (Exception e) {
                // Xử lý lỗi, ví dụ: ghi log, gửi thông báo lỗi
                e.printStackTrace();
                req.setAttribute("errorMessage", "An error occurred. Please try again.");
                req.getRequestDispatcher("changePasswordForm.jsp").forward(req, resp);
                return;
            }
        } else {
            // Nếu không trùng khớp, gửi lại thông báo lỗi
            req.setAttribute("errorMessage", "Passwords do not match. Please try again.");
            req.getRequestDispatcher("changePasswordForm.jsp").forward(req, resp);
            return;
        }

    }

    public static boolean validatePassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        char firstChar = password.charAt(0);
        if (!Character.isUpperCase(firstChar)) {
            return false;
        }
        return true;
    }
}
