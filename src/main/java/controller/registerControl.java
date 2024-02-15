package controller;

import dal.userDao;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Random;

@WebServlet(name = "registerControl", urlPatterns = "/signup")
public class registerControl extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userDao ud = new userDao();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String cfPassword = req.getParameter("confirmPassword");
        String agree = req.getParameter("agree");

        if (email == null || email.isEmpty() ||
                password == null || password.isEmpty() ||
                cfPassword == null || cfPassword.isEmpty()) {
            req.setAttribute("errorMessage", "Please input your information.");
            req.getRequestDispatcher("SignUp.jsp").forward(req, resp);
            return;
        }

        if (!validateEmail(email)) {
            req.setAttribute("errorMessage", "Wrong email format!");
            req.getRequestDispatcher("SignUp.jsp").forward(req, resp);
            return;
        }

        if (!validatePassword(password)) {
            req.setAttribute("errorMessage", "Password must contain 8 characters with the first letters uppercase.");
            req.getRequestDispatcher("SignUp.jsp").forward(req, resp);
            return;
        }

        if ("on".equals(agree)) {
            if (!ud.checkEmailExists(email)) {
                if (password.equals(cfPassword)) {
                    String username = generateUsername(email);
                    boolean checked = ud.registerUser(new User(username, password, email, false));
                    if (checked) {
                        resp.sendRedirect("SignIn.jsp");
                    } else {
                        req.setAttribute("errorMessage", "Sign up successfully!");
                        req.getRequestDispatcher("SignUp.jsp").forward(req, resp);
                    }
                }else {
                    req.setAttribute("errorMessage", "Please enter correct confirm password");
                    req.getRequestDispatcher("SignUp.jsp").forward(req, resp);
                }
            } else {
                req.setAttribute("errorMessage", "Email is existed!");
                req.getRequestDispatcher("SignUp.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("errorMessage", "Please agree with terms and policy.");
            req.getRequestDispatcher("SignUp.jsp").forward(req, resp);
        }
    }

    private static String generateUsername(String email) {
        String namePart = email.split("@")[0];

        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000);

        String username = namePart + randomNumber;

        return username;
    }

    public static boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
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
