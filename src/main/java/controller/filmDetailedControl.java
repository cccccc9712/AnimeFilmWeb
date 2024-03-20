package controller;

import dal.*;
import dtos.commentDto;
import dtos.filmDtos;
import dtos.seasonDtos;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "filmDetailedControl", urlPatterns = "/detail")
public class filmDetailedControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Integer userId = (Integer) session.getAttribute("userId");

        String filmName = req.getParameter("filmName");
        filmDao fd = new filmDao();
        episodeDao ed = new episodeDao();
        commentDao cmd = new commentDao();
        ratingDao rd = new ratingDao();

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

        int currentPage = 1;
        if (req.getParameter("page") != null) {
            currentPage = Integer.parseInt(req.getParameter("page"));
        }
        int commentsPerPage = 6;

        int filmId = fd.getFilmIdByFilmName(filmName);
        filmDtos film = fd.getFilmByName(filmName);
        List<seasonDtos> seasonList = ed.getSeasonsForFilm(filmName);
        List<filmDtos> listFilmMayLike = fd.getRandom6FilmsWithFullDetails();

        //If user log in, get favourite films and user rating for film
        if (userId  != null) {
            List<filmDtos> favouriteFilms = fd.getFavouriteFilmsByUserId(userId);
            Float userRating = rd.getUserRatingForFilm(userId, filmId);
            req.setAttribute("userRating", userRating);
            req.setAttribute("favouriteFilms", favouriteFilms);
        }

        //get film per page using offset and limit to get comments with paginator
        List<commentDto> comments = cmd.getCommentsPerPage(filmId, currentPage, commentsPerPage);
        int totalComments = cmd.getTotalCommentsForFilm(filmId);
        int totalPages = (int) Math.ceil(totalComments * 1.0 / commentsPerPage);

        //get replies for each comment
        for (commentDto comment : comments) {
            List<commentDto> replies = cmd.getRepliesByCommentId(comment.getCommentID());
            comment.setReplies(replies);
        }

        req.setAttribute("totalComments", totalComments);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("cmt", comments);
        req.setAttribute("rdFilms", listFilmMayLike);
        req.setAttribute("film", film);
        req.setAttribute("season", seasonList);
        req.getRequestDispatcher("SeriesDetailed.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
