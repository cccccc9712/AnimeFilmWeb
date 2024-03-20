package controller;

import dal.userDao;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "loginControl", urlPatterns = {"/login"})
public class loginControl extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String mail = req.getParameter("mail");
        String password = req.getParameter("pass");
        boolean remember = req.getParameter("remember") != null;

        if (mail.isBlank() || password.isBlank()) {
            req.setAttribute("failedLoginMessage", "Please input to sign in!");
            req.getRequestDispatcher("SignIn.jsp").forward(req, resp);
            return;
        }

        if (!validateEmail(mail)) {
            req.setAttribute("failedLoginMessage", "Wrong email format!");
            req.getRequestDispatcher("SignIn.jsp").forward(req, resp);
            return;
        }

        userDao dao = new userDao();
        User user = dao.login(mail, password);
        if (user == null) {
            req.setAttribute("failedLoginMessage", "Wrong Gmail or Password!");
            req.getRequestDispatcher("SignIn.jsp").forward(req, resp);
            return;
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userSession", user);

            if(remember){
                String rememberToken = UUID.randomUUID().toString();
                dao.saveUserRememberToken(user.getUserId(), rememberToken);
                Cookie rememberMeCookie = new Cookie("rememberMe", rememberToken);
                rememberMeCookie.setMaxAge(30 * 24 * 60 * 60);
                resp.addCookie(rememberMeCookie);
            }
            }
            resp.sendRedirect("home");
        }


    public static boolean validateEmail(String email) {
        //Regex for email: example.email1@example.co.uk; user.name+tag@example.com; first.last@example.com.vn
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }
}
