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

        //If the episode is not premium, redirect to the watching page
        if (!isEpisodePremium) {
            resp.sendRedirect("watching?episodeId=" + episodeId + "&filmId=" + filmId);
        } else {
            //If user has not logged in, redirect to the login page
            if (user == null) {
                req.setAttribute("failedLoginMessage", "Login to watch premium movies.");
                req.getRequestDispatcher("SignIn.jsp").forward(req, resp);
                return;
            } else {
                //If user is an admin or a premium user, redirect to the watching page
                userDao ud = new userDao();
                boolean isAdmin = user.getAdmin();
                boolean userPremium = ud.checkUserPremiumStatus(user.getUserId());
                if (userPremium || isAdmin) {
                    resp.sendRedirect("watching?episodeId=" + episodeId + "&filmId=" + filmId);
                } else {
                    //If user is not an admin or a premium user, redirect to the pricing page to register for premium
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
