package controller;

import dal.episodeDao;
import dal.filmDao;
import dal.userDao;
import dtos.filmDtos;
import dtos.newestEpisodeDto;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "homeControl", urlPatterns = "/home")
public class homeControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Integer userId = null;

        if (session != null) {
            userId = (Integer) session.getAttribute("userId");
            if (userId == null) {
                Cookie[] cookies = req.getCookies();
                String rememberMeToken = null;
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("rememberMe".equals(cookie.getName())) {
                            rememberMeToken = cookie.getValue();
                            break;
                        }
                    }

                    if (rememberMeToken != null) {
                        userDao dao = new userDao();
                        User user = dao.getUserByRememberMeToken(rememberMeToken);
                        if (user != null) {
                            session = req.getSession(true);
                            session.setAttribute("userId", user.getUserId());
                            session.setAttribute("userSession", user);
                            userId = user.getUserId();
                        }
                    }
                }
            }
        }

        filmDao fd = new filmDao();
        episodeDao ed = new episodeDao();
        List<filmDtos> trendingFilms = fd.getFilmWithHighestViewCount();
        List<filmDtos> newFilms = fd.getNewFilms();
        List<newestEpisodeDto> latestEpisodes = fd.getLatestEpisodes();
        List<newestEpisodeDto> premiumEpisodes = fd.getPremiumEpisodes();
        if (userId != null) {
            List<filmDtos> favouriteFilms = fd.getFavouriteFilmsByUserId(userId);
            List<newestEpisodeDto> watchedEpisodes = ed.getWatchedEpisodesByUserId(userId);
            req.setAttribute("favouriteFilms", favouriteFilms);
            req.setAttribute("watchedEpisodes", watchedEpisodes);
        }

        req.setAttribute("latestEpisodes", latestEpisodes);
        req.setAttribute("premiumEpisodes", premiumEpisodes);
        req.setAttribute("films", trendingFilms);
        req.setAttribute("newFilms", newFilms);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}