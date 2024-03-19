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

        int limit = 12;
        String numEpisodesParam = req.getParameter("numEpisodesToShow");
        if (numEpisodesParam != null) {
            try {
                limit = Integer.parseInt(numEpisodesParam) + 12;
            } catch (NumberFormatException e) {
                limit = 12;
            }
        }

        int premiumLimit = 6;
        String numPremiumEpisodesParam = req.getParameter("numPremiumEpisodesToShow");
        if (numPremiumEpisodesParam != null) {
            try {
                premiumLimit = Integer.parseInt(numPremiumEpisodesParam) + 6;
            } catch (NumberFormatException e) {
                premiumLimit = 6;
            }
        }
            req.setAttribute("numPremiumEpisodesToShow", premiumLimit);

            List<newestEpisodeDto> premiumEpisodes = ed.getPremiumEpisodesLimited(premiumLimit);
            req.setAttribute("premiumEpisodes", premiumEpisodes);
            int totalPremiumEpisodes = ed.countTotalPremiumEpisodes();
        // Kiểm tra xem có cần hiển thị nút "Show more" không
            boolean showMorePremium = premiumLimit < totalPremiumEpisodes;
            req.setAttribute("showMorePremium", showMorePremium);


            int totalEpisodes = fd.countTotalEpisodes();
            // Kiểm tra xem có cần hiển thị nút "Show more" không
            boolean showMore = limit < totalEpisodes;
            req.setAttribute("showMore", showMore);
            req.setAttribute("numEpisodesToShow", limit);

            List<filmDtos> trendingFilms = fd.getFilmWithHighestViewCount();
            List<filmDtos> newFilms = fd.getNewFilms();
            List<newestEpisodeDto> latestEpisodes = fd.getLatestEpisodes(limit);
            if (userId != null) {
                List<filmDtos> favouriteFilms = fd.getFavouriteFilmsByUserId(userId);
                List<newestEpisodeDto> watchedEpisodes = ed.getWatchedEpisodesByUserId(userId);
                req.setAttribute("favouriteFilms", favouriteFilms);
                req.setAttribute("watchedEpisodes", watchedEpisodes);
            }


            req.setAttribute("latestEpisodes", latestEpisodes);
            req.setAttribute("films", trendingFilms);
            req.setAttribute("newFilms", newFilms);
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }
