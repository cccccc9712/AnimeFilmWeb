package controller;

import dal.commentDao;
import dal.episodeDAO;
import dal.filmDao;
import dtos.commentDto;
import dtos.filmDtos;
import dtos.seasonDtos;
import entity.Comment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "filmDetailedControl", urlPatterns = "/detail")
public class filmDetailedControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filmName = req.getParameter("filmName");
        PrintWriter out = new PrintWriter(System.out);
        filmDao fd = new filmDao();
        episodeDAO ed = new episodeDAO();
        commentDao cmd = new commentDao();

        int currentPage = 1;
        if (req.getParameter("page") != null) {
            currentPage = Integer.parseInt(req.getParameter("page"));
        }
        int commentsPerPage = 6;

        int filmId = fd.getFilmIdByFilmName(filmName);
        filmDtos film = fd.getFilmByName(filmName);
        List<seasonDtos> seasonList = ed.getSeasonsForFilm(filmName);
        List<filmDtos> listFilmMayLike = fd.getRandom6FilmsWithFullDetails();

        List<commentDto> comments = cmd.getCommentsPerPage(filmId, currentPage, commentsPerPage);
        int totalComments = cmd.getTotalCommentsForFilm(filmId);
        int totalPages = (int) Math.ceil(totalComments * 1.0 / commentsPerPage);

        for (commentDto comment : comments) {
            List<commentDto> replies = cmd.getRepliesByCommentId(comment.getCommentID());
            comment.setReplies(replies);
        }

        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("cmt", comments);
        req.setAttribute("rdFilms", listFilmMayLike);
        req.setAttribute("film", film);
        req.setAttribute("season", seasonList);
        req.getRequestDispatcher("SeriesDetailed.jsp").forward(req, resp);
    }

}
