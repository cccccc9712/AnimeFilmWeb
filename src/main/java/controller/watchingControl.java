package controller;

import dal.episodeDao;
import dal.filmDao;
import dtos.episodeDtos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Time;
import java.util.Date;

@WebServlet(name = "watchingControl", urlPatterns = "/watching")
public class watchingControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String episodeIdStr = req.getParameter("episodeId");
        String filmIdStr = req.getParameter("filmId");
        Integer userId = (Integer) session.getAttribute("userId");
        Date watchingDate = new Date();
        Time watchingTime = new Time(System.currentTimeMillis());
        filmDao fd = new filmDao();
        episodeDao episodeDao = new episodeDao();

        if (episodeIdStr != null && !episodeIdStr.isEmpty() && filmIdStr != null && !filmIdStr.isEmpty()) {
            int episodeId = Integer.parseInt(episodeIdStr);
            int filmId = Integer.parseInt(filmIdStr);
            episodeDao ed = new episodeDao();
            episodeDtos episode = ed.getEpisodeById(episodeId);
            ed.increaseViewCount(filmId);

            if (userId  != null) {
                try {
                    episodeDao.saveWatchedHistory(userId, filmId, episodeId, new java.sql.Date(watchingDate.getTime()), watchingTime);
                } catch (NumberFormatException e) {

                }
            }

            req.setAttribute("episode", episode);
            req.getRequestDispatcher("Watching.jsp").forward(req, resp);
        }
    }

}
