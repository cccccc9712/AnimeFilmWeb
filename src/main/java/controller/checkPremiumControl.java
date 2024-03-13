package controller;

import dal.episodeDao;
import dal.userDao;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "checkPremiumControl", urlPatterns = "/CheckPremium")
public class checkPremiumControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("userSession");

        int episodeId = Integer.parseInt(req.getParameter("episodeId"));
        int filmId = Integer.parseInt(req.getParameter("filmId"));

        episodeDao ed = new episodeDao();
        boolean isEpisodePremium = ed.checkEpisodeIsPremium(episodeId);

        // Nếu tập phim không phải là premium, chuyển hướng người dùng đến trang xem phim
        if (!isEpisodePremium) {
            resp.sendRedirect("watching?episodeId=" + episodeId + "&filmId=" + filmId);
        } else {
            if (user == null) {
                req.setAttribute("failedLoginMessage", "Login to watch premium movies.");
                req.getRequestDispatcher("SignIn.jsp").forward(req, resp);
                return;
            } else {
                userDao ud = new userDao();
                boolean isAdmin = user.getAdmin();
                boolean userPremium = ud.checkUserPremiumStatus(user.getUserId());
                if (userPremium || isAdmin) {
                    resp.sendRedirect("watching?episodeId=" + episodeId + "&filmId=" + filmId);
                } else {
                    // Nếu người dùng không có premium, chuyển hướng họ đến trang giới thiệu gói premium
                    resp.sendRedirect("pricing.jsp");
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
